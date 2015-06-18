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
  public Result index(Context context) {
    Result result =
        Results.ok().html().template("/app/index.html")
            .render("showMinifiedVersion", propertyService.showMinifiedVersion());
    addLanguageAndDateFormat(context, result);
    return result;
  }

  @GET
  @Path("{template: .*}\\.tpl\\.html")
  public Result getTemplate(Context context, @PathParam("template") String templateName) {
    Result result = Results.ok().html().template("/app" + templateName + ".tpl.html");
    addLanguageAndDateFormat(context, result);
    return result;
  }

  @GET
  @Path("/api/buildProperties")
  public Result getBuildProperties() {
    return Results.ok().json().render("buildProperties", propertyService.getBuildProperties());
  }

  @GET
  @Path("{file: .*}.js")
  public Result getCustomJavascriptModuleFiles(@PathParam("file") String fileName) {
    return Results.ok().html().template("/app" + fileName + ".js");
  }

  private void addLanguageAndDateFormat(Context context, final Result result) {
    result.render("language", localizationService.getLanguage(context).or("en"));
    result.render("dateFormat", localizationService.getPattern(context));
  }
}
