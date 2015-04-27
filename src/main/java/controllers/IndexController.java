package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import ninja.Result;
import ninja.Results;
import ninja.jaxy.GET;
import ninja.jaxy.Path;
import ninja.params.PathParam;
import ninja.utils.NinjaProperties;

@Singleton
@Path("")
public class IndexController {

  @Inject
  private NinjaProperties ninjaProperties;

  @GET
  @Path("^((?!(\\/api\\/|tpl)).)*$")
  public Result index() {
    return Results.ok().html().template("/app/index.html").render("isProd", isProd());
  }

  private boolean isProd() {
    return !(ninjaProperties.isTest() || ninjaProperties.isDev());
  }

  @GET
  @Path("{template: .*}\\.tpl\\.html")
  public Result getTemplate(@PathParam("template") String templateName) {
    return Results.ok().html().template("/app" + templateName + ".tpl.html");
  }

  @GET
  @Path("/dist/{file: .*}")
  public Result customJavascriptModuleFiles(@PathParam("file") String fileName) {
    System.out.println(fileName);
    return Results.ok().html().template("/app/dist/" + fileName);
  }


}
