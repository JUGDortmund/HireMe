package controllers.api;


import com.google.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.jaxy.DELETE;
import ninja.jaxy.Dev;
import ninja.jaxy.GET;
import ninja.jaxy.POST;
import ninja.jaxy.PUT;
import ninja.jaxy.Path;

/**
 * The type Mock controller.
 *
 * Use this controller to simulate Restful Services
 * within hireMe. Useful when backend functionality
 * isn't provided yet and you want to test your
 * angularJs Rest-functions.
 *
 * Only provided in dev-mode!
 */
@Singleton
@Path("/api/mock")
public class MockController {

  Logger LOG = LoggerFactory.getLogger(MockController.class);

  @Dev
  @GET
  @Path("")
  public Result getMock()
  {
    LOG.info("[GET] Reached getMock() in MockController---> Returning statusCode \"200\"");
    return Results.status(200).json().render("method", "getMock").render("createdBy", "MockController");
  }

  @Dev
  @GET
  @Path("/notfound")
  public Result getNotFoundMock()
  {
    LOG.info("[GET] Reached getNotFoundMock() in MockController---> Returning statusCode \"404\"");
    return Results.status(404).json().render("method", "getNotFoundMock").render("createdBy", "MockController");
  }

  @Dev
  @PUT
  @Path("")
  public Result putMock()
  {
    LOG.info("[PUT] Reached putMock() in MockController---> Returning statusCode \"200\"");
    return Results.status(200).json().render("method", "putMock").render("createdBy", "MockController");
  }

  @Dev
  @PUT
  @Path("/notfound")
  public Result putNotFoundMock(){
    LOG.info("[PUT] Reached putNotFoundMock() in MockController---> Returning statusCode \"404\"");
    return Results.status(404).json().render("method", "putNotFoundMock").render("createdBy", "MockController");
  }

  @Dev
  @PUT
  @Path("/nocontent")
  public Result putNoContentMock(){
    LOG.info("[PUT] Reached putNoContentMock() in MockController---> Returning statusCode \"204\"");
    return Results.status(204).json().render("method", "putNoContentMock").render("createdBy", "MockController");
  }

  @Dev
  @POST
  @Path("")
  public Result postMock(Context context) {
    LOG.info("[POST] Reached postMock() in MockController---> Returning statusCode \"201\"");
    return Results.status(201).json().render("method", "postMock").render("createdBy", "MockController");
  }

  @Dev
  @POST
  @Path("")
  public Result postNotFoundMock() {
    LOG.info("[POST] Reached postNotFoundMock() in MockController---> Returning statusCode \"404\"");
    return Results.status(404).json().render("method", "postNotFoundMock").render("createdBy", "MockController");
  }

  @Dev
  @DELETE
  @Path("")
  public Result deleteMock() {
    LOG.info("[DELETE] Reached deleteMock() in MockController---> Returning statusCode \"200\"");
    return Results.status(200).json().render("method", "deleteMock").render("createdBy", "MockController");
  }

  @Dev
  @DELETE
  @Path("/notfound")
  public Result deleteNotFoundMock() {
    LOG.info("[DELETE] Reached deleteNotFoundMock() in MockController---> Returning statusCode \"404\"");
    return Results.status(404).json().render("method", "deleteNotFoundMock").render("createdBy", "MockController");
  }
}
