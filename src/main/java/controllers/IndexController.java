package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;

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

  @Inject
  private ObjectMapper objectMapper;

  @GET
  @Path("^((?!(\\/api\\/|tpl)).)*$")
  public Result index() {
    Result result = Results.html();

    result.render("renderGitProperties", Boolean.toString(propertyService.renderGitProperties()));
    /*result.render("git.commit.id.abbrev", propertyService.getString("git.commit.id.abbrev"));
    result.render("git.commit.user.name", propertyService.getString("git.commit.user.name"));
    result.render("git.branch", propertyService.getString("git.branch"));
    result.render("git.commit.time", propertyService.getString("git.commit.time"));
    result.render("git.build.time", propertyService.getString("git.build.time"));*/

    result.template("/app/index.html");

    return result;
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
