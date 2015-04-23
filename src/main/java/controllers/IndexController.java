package controllers;

import com.google.inject.Singleton;

import ninja.Result;
import ninja.Results;
import ninja.jaxy.GET;
import ninja.jaxy.Path;
import ninja.params.PathParam;

@Singleton
@Path("")
public class IndexController {

  @GET
  @Path("^((?!(\\/api\\/|tpl)).)*$")
  public Result index() {
    return Results.ok().html().template("/app/index.html");
  }

  @GET
  @Path("{template: .*}\\.tpl\\.html")
  public Result getTemplate(@PathParam("template") String templateName) {
    return Results.ok().html().template("/app" + templateName + ".tpl.html");
  }

  @GET
  @Path("{file: .*}.js")
  public Result customJavascriptModuleFiles(@PathParam("file") String fileName) {
    return Results.ok().html().template("/app" + fileName + ".js");
  }


}
