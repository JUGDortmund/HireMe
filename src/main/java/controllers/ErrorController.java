package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import services.PropertyService;

import ninja.Result;
import ninja.Results;
import ninja.jaxy.GET;
import ninja.jaxy.Path;

@Singleton
@Path("")
public class ErrorController {

  @Inject
  private PropertyService propertyService;

  @GET
  @Path("/400")
  public Result badRequest() {
    return renderStatusCode(Result.SC_400_BAD_REQUEST);
  }

  @GET
  @Path("/404")
  public Result notFound() {
    return renderStatusCode(Result.SC_404_NOT_FOUND);
  }

  @GET
  @Path("/403")
  public Result forbidden() {
    return renderStatusCode(Result.SC_403_FORBIDDEN);
  }

  @GET
  @Path("/401")
  public Result unauthorized() {
    return renderStatusCode(Result.SC_401_UNAUTHORIZED);
  }

  @GET
  @Path("/500")
  public Result internalServerError() {
    return renderStatusCode(Result.SC_500_INTERNAL_SERVER_ERROR);
  }

  private Result renderStatusCode(int statusCode) {
    return Results.status(statusCode).html().template("/app/index.html")
        .render("showMinifiedVersion", propertyService.showMinifiedVersion());
  }
}
