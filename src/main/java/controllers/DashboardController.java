package controllers;

import com.google.inject.Singleton;

import ninja.Result;
import ninja.Results;
import ninja.jaxy.GET;
import ninja.jaxy.Path;

@Singleton
@Path("/.*")
public class DashboardController {

  @GET
  public Result index() {
    return Results.html();
  }

}
