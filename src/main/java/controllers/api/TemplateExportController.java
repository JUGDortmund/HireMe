package controllers.api;

import javax.inject.Inject;

import ninja.Result;
import ninja.Results;
import ninja.jaxy.GET;
import ninja.jaxy.Path;
import services.TemplateExportService;

@Path("(?i)/api/template")
public class TemplateExportController {

  TemplateExportService exportTemplate;

  @Inject
  public TemplateExportController(TemplateExportService exportTemplate) {
    this.exportTemplate = exportTemplate;
  }

  @Path("")
  @GET
  public Result getTemplates() {
    return Results.json().render(exportTemplate.getTemplateDefinitions());
  }
}
