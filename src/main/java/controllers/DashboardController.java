package controllers;

import ninja.Result;
import ninja.Results;

import com.google.inject.Singleton;

@Singleton
public class DashboardController {

  public Result index() {
    return Results.html();
  }

}
