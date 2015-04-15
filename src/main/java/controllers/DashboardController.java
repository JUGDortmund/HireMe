package controllers;

import com.google.inject.Singleton;
import ninja.Result;
import ninja.Results;
import ninja.jaxy.GET;
import ninja.jaxy.Path;

@Singleton
public class DashboardController {

    @GET
    @Path("/")
    public Result index() {
        return Results.html();
    }

}
