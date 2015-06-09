package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.reflect.ClassPath;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lowagie.text.DocumentException;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import model.Profile;
import model.Template;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.output.StringBuilderWriter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextUserAgent;
import util.ReflectionUtil;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.reflections.ReflectionUtils.getFields;

@Singleton
public class ProfileExportService {
  
  public static final String TEMPLATES_PATH = "exportTemplates";
  private final ObjectMapper mapper;
  private final Configuration configuration;
  private List<Template> loadedTemplates;

  @Inject
  public ProfileExportService(final ObjectMapper mapper, final Configuration configuration) {
    this.mapper = mapper;
    this.configuration = configuration;
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
    template.process(profileToMap(profile), writer);
    return createPDFAsBytes(writer.toString());
  }

  @SuppressWarnings("unchecked")
  private Map profileToMap(@NotNull Profile profile) throws IllegalAccessException {
    final Map dataModel = mapper.convertValue(profile, Map.class);
    for (final Field field : getFields(profile.getClass())) {
      field.setAccessible(true);
      final Object value = field.get(profile);
      if (value == null) {
        dataModel.put(field.getName(), "");
      } else if (ReflectionUtil.isStringList(field)) {
        dataModel.put(field.getName(), ((List<String>) value).stream().reduce("", (acc, e) -> {
          if (acc.equals("")) {
            return e;
          } else {
            return acc + ", " + e;
          }
        }));
      }
    }
    return dataModel;
  }

  @NotNull
  private byte[] createPDFAsBytes(@NotNull final String source) throws DocumentException, IOException, URISyntaxException {
    final ITextRenderer renderer = new ITextRenderer();
    class PackageUserAgentCallback extends ITextUserAgent {

      public PackageUserAgentCallback(final ITextOutputDevice outputDevice) {
        super(outputDevice);
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

    renderer.getSharedContext().setUserAgentCallback(new PackageUserAgentCallback(renderer.getOutputDevice()));
    renderer.getSharedContext().setBaseURL("/exportTemplates/");
    try (final ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
      renderer.setDocumentFromString(source, "/exportTemplates/");
      NodeList images = renderer.getDocument().getElementsByTagName("img");
      for (int i = 0; i < images.getLength(); i++) {
        final Node image = images.item(i);
        image.getAttributes().getNamedItem("src").setNodeValue("data:image/png;base64," + Base64.encodeBase64String(Files.readAllBytes(Paths.get(getClass().getResource("/exportTemplates/" + image.getAttributes().getNamedItem("src").getNodeValue()).toURI()))));
        int u = 0;
        u++;
      }
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
