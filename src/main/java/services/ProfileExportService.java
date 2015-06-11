package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Preconditions;
import com.google.common.reflect.ClassPath;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lowagie.text.DocumentException;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import model.BaseModel;
import model.Profile;
import model.Resource;
import model.Template;
import model.annotations.Tag;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang3.StringUtils;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextUserAgent;
import util.ReflectionUtil;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.reflections.ReflectionUtils.getFields;

@Singleton
public class ProfileExportService {
  
  public static final String TEMPLATES_PATH = "exportTemplates";
  public static final SimpleDateFormat FORMAT = new SimpleDateFormat("MMMM yyyy", Locale.GERMAN);
  private final ObjectMapper mapper;
  private final Configuration configuration;
  private List<Template> loadedTemplates;

  @Inject
  public ProfileExportService(final ObjectMapper mapper, final Configuration configuration) {
    this.mapper = mapper;
    this.configuration = configuration;

    SimpleModule module = new SimpleModule();
    module.addSerializer(Resource.class, null);
    mapper.registerModule(module);
  }

  @NotNull
  public byte[] exportToPdf(@NotNull final Profile profile) {
    Preconditions.checkNotNull(profile);
    try {
      return tryExportToPDF(profile);
    } catch (final IOException | URISyntaxException | TemplateException | DocumentException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull
  private byte[] tryExportToPDF(@NotNull final Profile profile) throws IOException, TemplateException, DocumentException, IllegalAccessException, URISyntaxException {

    final freemarker.template.Template template = configuration.getTemplate(getTemplates().get(0)
      .getTemplatePath());

    final StringBuilderWriter writer = new StringBuilderWriter();
    template.process(transformModelToTemplateValues(profile), writer);
    return createPDFAsBytes(writer.toString(), profile);
  }

  @SuppressWarnings("unchecked")
  private <T extends BaseModel> Map transformModelToTemplateValues(final @NotNull T model) {
    try {
      final Map dataModel = mapper.convertValue(model, Map.class);
      for (final Field field : getFields(model.getClass())) {
        field.setAccessible(true);
        Optional<Object> result = handleField(field, model);
        if (result.isPresent()) {
          dataModel.put(field.getName(), result.get());
        }
      }
      return dataModel;
    } catch (final IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  private <T extends BaseModel> Optional<Object> handleField(final @NotNull Field field, T model) throws IllegalAccessException {
    Object result = null;
    final Object value = field.get(model);
    if (value == null) {
      result = handleNullValue(field);
    } else if (field.getType().isAssignableFrom(Date.class)) {
      result = FORMAT.format(value);
    } else if (BaseModel.class.isAssignableFrom(field.getType())) {
      result = transformModelToTemplateValues((T) value);
    } else if (ReflectionUtil.isModelList(field)) {
      result =
        ((List<T>) value)
          .stream()
          .map(this::transformModelToTemplateValues)
          .collect(Collectors.toList());
    } else if (field.getType().isAssignableFrom(BaseModel.class)) {
      result = transformModelToTemplateValues((T) value);
    } else if (ReflectionUtil.isStringList(field) &&
      (field.getAnnotation(Tag.class) == null || !field.getAnnotation(Tag.class).excludeFromStringConcatenation())) {
      result = ((List<String>) value)
        .stream()
        .reduce((acc, e) -> acc + ", " + e)
        .orElse("");
    }
    return Optional.ofNullable(result);
  }

  private Object handleNullValue(@NotNull final Field field) {
    if (List.class.isAssignableFrom(field.getType()) && field.getAnnotation(Tag.class) != null && field.getAnnotation(Tag.class).excludeFromStringConcatenation()) {
      return new ArrayList<>();
    }
    return StringUtils.EMPTY;
  }

  @NotNull
  private byte[] createPDFAsBytes(@NotNull final String source, @NotNull final Profile profile) throws DocumentException, IOException, URISyntaxException {
    final ITextRenderer renderer = new ITextRenderer();
    class PackageUserAgentCallback extends ITextUserAgent {

      private final Profile profile;

      public PackageUserAgentCallback(@NotNull final ITextOutputDevice outputDevice, @NotNull final Profile profile) {
        super(outputDevice);
        this.profile = profile;
      }

      @Override
      protected InputStream resolveAndOpenStream(String uri) {
        if (uri.equals("/exportTemplates/profileImage.png") && profile.getImage() != null) {
          return new ByteArrayInputStream(profile.getImage().getContent());
        }
        return getClass().getResourceAsStream(uri);
      }

      @Override
      public byte[] getBinaryResource(String uri) {
        try {
          return Files.readAllBytes(Paths.get("/" + uri));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }

    }
    PackageUserAgentCallback callback = new PackageUserAgentCallback(renderer.getOutputDevice(), profile);
    callback.setSharedContext(renderer.getSharedContext());
    renderer.getSharedContext().setUserAgentCallback(callback);

    try (final ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
      renderer.setDocumentFromString(source, "/exportTemplates/");
      renderer.layout();
      renderer.createPDF(stream, false);
      renderer.finishPDF();

      return stream.toByteArray();
    }
  }

  @NotNull
  public List<Template> getTemplates() {
    if (loadedTemplates == null) {
      loadTemplates();
    }
    return loadedTemplates;
  }

  @NotNull
  private void loadTemplates() {
    try {
      loadedTemplates =
        ClassPath.from(Thread.currentThread().getContextClassLoader())
          .getResources()
          .stream()
          .filter(x ->
            x.getResourceName().startsWith(TEMPLATES_PATH) &&
              x.getResourceName().endsWith(".json"))
          .map(getTransformer())
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull
  private <T extends ClassPath.ResourceInfo> Function<T, Template> getTransformer() {
    return t -> {
      try {
        return mapper.readValue(new String(Files.readAllBytes(Paths.get(t.url().toURI()))),
          Template.class);
      } catch (IOException | URISyntaxException e) {
        throw new RuntimeException(e);
      }
    };
  }
}
