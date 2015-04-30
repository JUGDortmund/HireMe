package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import ninja.Result;
import ninja.Results;
import ninja.jaxy.GET;
import ninja.jaxy.Path;
import ninja.params.PathParam;
import services.PropertyService;

@Singleton
@Path("")
public class IndexController {

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
  public Result getTemplate(@PathParam("template") String templateName) {
    return Results.ok().html().template("/app" + templateName + ".tpl.html");
  }

  @GET
  @Path("/api/gitProperties")
  public Result getGitPropertyDTO() {
    return Results.ok().json().render("getGitPropertyDTO", propertyService.getGitPropertyDTO());
  }

  @GET
  @Path("{file: .*}.js")
  public Result getCustomJavascriptModuleFiles(@PathParam("file") String fileName) {
    return Results.ok().html().template("/app" + fileName + ".js");
  }


}
