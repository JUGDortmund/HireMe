package controllers.api;


import com.google.inject.Singleton;

import org.slf4j.Logger;

import model.annotations.InjectLogger;

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.jaxy.DELETE;
import ninja.jaxy.Dev;
import ninja.jaxy.GET;
import ninja.jaxy.POST;
import ninja.jaxy.PUT;
import ninja.jaxy.Path;
import ninja.jaxy.Test;

/**
 * The type Mock controller.
 *
 * Use this controller to simulate Restful Services within hireMe. Useful when backend functionality
 * isn't provided yet and you want to test your angularJs Rest-functions.
 *
 * Only provided in dev-mode!
 */
@Singleton
@Path("/api/mock")
public class MockController {

  @InjectLogger
  private Logger LOG;

  @Dev
  @Test
  @GET
  @Path("")
  public Result getMock() {
    LOG.info("[GET] Reached getMock() in MockController---> Returning statusCode \"200\"");
    return Results.status(Result.SC_200_OK).json().render("method", "getMock")
        .render("createdBy", "MockController");
  }

  @Dev
  @Test
  @GET
  @Path("/notfound")
  public Result getNotFoundMock() {
    LOG.info("[GET] Reached getNotFoundMock() in MockController---> Returning statusCode \"404\"");
    return Results.status(Result.SC_404_NOT_FOUND).json().render("method", "getNotFoundMock")
        .render("createdBy", "MockController");
  }

  @Dev
  @Test
  @PUT
  @Path("")
  public Result putMock() {
    LOG.info("[PUT] Reached putMock() in MockController---> Returning statusCode \"200\"");
    return Results.status(Result.SC_200_OK).json().render("method", "putMock")
        .render("createdBy", "MockController");
  }

  @Dev
  @Test
  @PUT
  @Path("/notfound")
  public Result putNotFoundMock() {
    LOG.info("[PUT] Reached putNotFoundMock() in MockController---> Returning statusCode \"404\"");
    return Results.status(Result.SC_404_NOT_FOUND).json().render("method", "putNotFoundMock")
        .render("createdBy", "MockController");
  }

  @Dev
  @Test
  @PUT
  @Path("/nocontent")
  public Result putNoContentMock() {
    LOG.info("[PUT] Reached putNoContentMock() in MockController---> Returning statusCode \"204\"");
    return Results.status(Result.SC_204_NO_CONTENT).json().render("method", "putNoContentMock")
        .render("createdBy", "MockController");
  }

  @Dev
  @Test
  @POST
  @Path("")
  public Result postMock(Context context) {
    LOG.info("[POST] Reached postMock() in MockController---> Returning statusCode \"201\"");
    return Results.status(Result.SC_201_CREATED).json().render("method", "postMock")
        .render("createdBy", "MockController");
  }

  @Dev
  @Test
  @POST
  @Path("")
  public Result postNotFoundMock() {
    LOG.info("[POST] Reached postNotFoundMock() in MockController---> Returning statusCode \"404\"");
    return Results.status(Result.SC_404_NOT_FOUND).json().render("method", "postNotFoundMock")
        .render("createdBy", "MockController");
  }

  @Dev
  @Test
  @DELETE
  @Path("")
  public Result deleteMock() {
    LOG.info("[DELETE] Reached deleteMock() in MockController---> Returning statusCode \"200\"");
    return Results.status(Result.SC_200_OK).json().render("method", "deleteMock")
        .render("createdBy", "MockController");
  }

  @Dev
  @Test
  @DELETE
  @Path("/notfound")
  public Result deleteNotFoundMock() {
    LOG.info("[DELETE] Reached deleteNotFoundMock() in MockController---> Returning statusCode \"404\"");
    return Results.status(Result.SC_404_NOT_FOUND).json().render("method", "deleteNotFoundMock")
        .render("createdBy", "MockController");
  }
}
