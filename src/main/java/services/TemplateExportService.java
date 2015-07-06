package services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.ExportTemplateDefinition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.reflect.ClassPath;


public class TemplateExportService {

  private List<ExportTemplateDefinition> loadedExportTemplateDefinitions;
  private ExportTemplateDefinition PDFTemplate;
  public static final String TEMPLATES_PATH = "exportTemplates";
  private final ObjectMapper mapper;

  @Inject
  public TemplateExportService(@NotNull final ObjectMapper mapper) {
    Preconditions.checkNotNull(mapper);
    this.mapper = mapper;
  }

  @NotNull
  public List<ExportTemplateDefinition> getTemplateDefinitions() {
    if (loadedExportTemplateDefinitions == null) {
      this.loadTemplates();
    }
    return loadedExportTemplateDefinitions;
  }

  @NotNull
  public ExportTemplateDefinition getTemplate(String template) {
    getTemplateDefinitions();
    for (ExportTemplateDefinition i : loadedExportTemplateDefinitions) {
      if (i.getName().contains(template)) {
        PDFTemplate = i;
      }
    }
    return PDFTemplate;
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
}
