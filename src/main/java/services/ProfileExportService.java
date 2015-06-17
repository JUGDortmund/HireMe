package services;

import static org.reflections.ReflectionUtils.getFields;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import com.google.common.reflect.ClassPath;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lowagie.text.DocumentException;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang3.StringUtils;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextUserAgent;

import util.ReflectionUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import model.BaseModel;
import model.ExportTemplateDefinition;
import model.Profile;
import model.annotations.ExcludeFromStringConcatenation;

@Singleton
public class ProfileExportService {

  public static final String TEMPLATES_PATH = "exportTemplates";
  public static final SimpleDateFormat FORMAT = new SimpleDateFormat("MMMM yyyy", Locale.GERMAN);
  private final ObjectMapper mapper;
  private final Configuration configuration;
  private List<ExportTemplateDefinition> loadedExportTemplateDefinitions;

  @Inject
  public ProfileExportService(@NotNull final ObjectMapper mapper,
      @NotNull final Configuration configuration) {
    Preconditions.checkNotNull(mapper);
    Preconditions.checkNotNull(configuration);

    this.mapper = mapper;
    this.configuration = configuration;
  }

  @NotNull
  public byte[] exportToPdf(@NotNull final Profile profile) {
    Preconditions.checkNotNull(profile);
    try {
      return tryExportToPDF(profile);
    } catch (final IOException | URISyntaxException | TemplateException | DocumentException
        | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull
  private byte[] tryExportToPDF(@NotNull final Profile profile) throws IOException,
      TemplateException, DocumentException, IllegalAccessException, URISyntaxException {

    final freemarker.template.Template template =
        configuration.getTemplate(getTemplateDefinitions().get(0).getTemplatePath());

    final StringBuilderWriter writer = new StringBuilderWriter();
    template.process(transformModelToTemplateValues(profile), writer);
    return createPDFAsBytes(writer.toString(), profile);
  }

  @SuppressWarnings("unchecked")
  private <T extends BaseModel> Map transformModelToTemplateValues(@NotNull final T model) {
    try {
      final Map dataModel = mapper.convertValue(model, Map.class);
      for (final Field field : getFields(model.getClass())) {
        field.setAccessible(true);
        final Optional<Object> result = handleField(field, model);
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
  private <T extends BaseModel> Optional<Object> handleField(@NotNull final Field field,
      @NotNull final T model) throws IllegalAccessException {
    Object result = null;
    final Object value = field.get(model);
    if (value == null) {
      result = handleNullValue(field);
    } else if (Date.class.isAssignableFrom(field.getType())) {
      result = FORMAT.format(value);
    } else if (BaseModel.class.isAssignableFrom(field.getType())) {
      result = transformModelToTemplateValues((T) value);
    } else if (ReflectionUtil.isModelList(field)) {
      result =
          ((List<T>) value).stream().map(this::transformModelToTemplateValues)
              .collect(Collectors.toList());
    } else if (ReflectionUtil.isStringList(field)
        && field.getAnnotation(ExcludeFromStringConcatenation.class) == null) {
      result = ((List<String>) value).stream().reduce((acc, e) -> acc + ", " + e).orElse("");
    }
    return Optional.ofNullable(result);
  }

  private Object handleNullValue(@NotNull final Field field) {
    if (List.class.isAssignableFrom(field.getType())
        && field.getAnnotation(ExcludeFromStringConcatenation.class) != null) {
      return new ArrayList<>();
    }
    return StringUtils.EMPTY;
  }

  @NotNull
  private byte[] createPDFAsBytes(@NotNull final String source, @NotNull final Profile profile)
      throws DocumentException, IOException, URISyntaxException {
    final ITextRenderer renderer = new ITextRenderer();

    PackageUserAgentCallback callback =
        new PackageUserAgentCallback(renderer.getOutputDevice(), profile);
    callback.setSharedContext(renderer.getSharedContext());
    renderer.getSharedContext().setUserAgentCallback(callback);

    try (final ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
      renderer.setDocumentFromString(source, "/" + TEMPLATES_PATH + "/");
      renderer.layout();
      renderer.createPDF(stream);
      renderer.finishPDF();

      return stream.toByteArray();
    }
  }

  @NotNull
  public List<ExportTemplateDefinition> getTemplateDefinitions() {
    if (loadedExportTemplateDefinitions == null) {
      loadTemplates();
    }
    return loadedExportTemplateDefinitions;
  }

  @NotNull
  private void loadTemplates() {
    try {
      loadedExportTemplateDefinitions =
          ClassPath
              .from(Thread.currentThread().getContextClassLoader())
              .getResources()
              .stream()
              .filter(
                  x -> x.getResourceName().startsWith(TEMPLATES_PATH)
                      && x.getResourceName().endsWith(".json")).map(getTransformer())
              .collect(Collectors.toList());
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull
  private <T extends ClassPath.ResourceInfo> Function<T, ExportTemplateDefinition> getTransformer() {
    return t -> {
      try {
        final Path pathToResource = Paths.get(t.url().toURI());
        return mapper.readValue(new String(Files.readAllBytes(pathToResource)),
            ExportTemplateDefinition.class);
      } catch (final IOException | URISyntaxException e) {
        throw new RuntimeException(e);
      }
    };
  }

  private static class PackageUserAgentCallback extends ITextUserAgent {

    private final Profile profile;

    public PackageUserAgentCallback(@NotNull final ITextOutputDevice outputDevice,
        @NotNull final Profile profile) {
      super(outputDevice);
      this.profile = profile;
    }

    @Override
    protected InputStream resolveAndOpenStream(@NotNull final String uri) {
      if (uri.equals("/" + TEMPLATES_PATH + "/defaultProfileImage.png")
          && profile.getImage() != null) {
        return new ByteArrayInputStream(profile.getImage().getContent());
      }
      return getClass().getResourceAsStream(uri);
    }

    @Override
    public byte[] getBinaryResource(@NotNull final String uri) {
      try {
        return ByteStreams.toByteArray(getClass().getResourceAsStream(uri));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

  }
}
