package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Lukas Eichler
 */
public class ExportTemplateDefinition {

  private final String name;
  private final String templatePath;
  private final String fileNamePattern;

  @JsonCreator
  public ExportTemplateDefinition(@JsonProperty("name") final String name,
                                  @JsonProperty("templatePath") final String templatePath,
                                  @JsonProperty("fileNamePattern") final String fileNamePattern) {
    this.name = name;
    this.templatePath = templatePath;
    this.fileNamePattern = fileNamePattern;
  }

  public String getName() {
    return name;
  }

  public String getTemplatePath() {
    return templatePath;
  }

  public String getFileNamePattern() {
    return fileNamePattern;
  }
}
