package controllers;

import ninja.Result;
import ninja.Results;
import ninja.jaxy.GET;
import ninja.jaxy.Path;
import com.google.inject.Singleton;

@Singleton
@Path("/.*")
public class DashboardController {

    @GET
    public Result index() {
        return Results.html();
    }
}
