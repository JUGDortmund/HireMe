package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import services.LocalizationService;
import services.PropertyService;

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.jaxy.GET;
import ninja.jaxy.Path;
import ninja.params.PathParam;

@Singleton
@Path("")
public class IndexController {

  @Inject
  LocalizationService localizationService;
  @Inject
  private PropertyService propertyService;

  @GET
  @Path("^((?!(\\/api\\/|tpl)).)*$")
  public Result index() {
    return Results.ok().html().template("/app/index.html")
        .render("showMinifiedVersion", propertyService.showMinifiedVersion());
  }

  @GET
  @Path("{template: .*}\\.tpl\\.html")
  public Result getTemplate(Context context, @PathParam("template") String templateName) {
    return Results.ok().html().template("/app" + templateName + ".tpl.html")
        .render("language", localizationService.getLanguage(context).or("en")).render("dateFormat",
                                                                                         localizationService
                                                                                             .getPattern(context));
  }

  @GET
  @Path("/api/buildProperties")
  public Result getBuildPropertyDTO() {
    return Results.ok().json().render("buildProperties", propertyService.getBuildProperties());
  }

  @GET
  @Path("{file: .*}.js")
  public Result getCustomJavascriptModuleFiles(@PathParam("file") String fileName) {
    return Results.ok().html().template("/app" + fileName + ".js");
  }
}
