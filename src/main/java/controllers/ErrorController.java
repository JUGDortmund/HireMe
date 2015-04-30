package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import ninja.Result;
import ninja.Results;
import ninja.jaxy.GET;
import ninja.jaxy.Path;
import services.PropertyService;

@Singleton
@Path("")
public class ErrorController {

  @Inject
  private PropertyService propertyService;

  @GET
  @Path("/400")
  public Result badRequest() {
    return renderStatusCode(400);
  }

  @GET
  @Path("/404")
  public Result notFound() {
    return renderStatusCode(404);
  }

  @GET
  @Path("/403")
  public Result forbidden() {
    return renderStatusCode(403);
  }

  @GET
  @Path("/401")
  public Result unauthorized() {
    return renderStatusCode(401);
  }

  @GET
  @Path("/500")
  public Result internalServerError() {
    return renderStatusCode(500);
  }

  private Result renderStatusCode(int statusCode) {
    return Results.status(statusCode).html().template("/app/index.html").render("showMinifiedVersion", propertyService.showMinifiedVersion());
  }
}
