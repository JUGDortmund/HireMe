package controllers.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.mongodb.morphia.Datastore;

import model.Profile;
import ninja.Result;
import ninja.Results;
import ninja.jaxy.GET;
import ninja.jaxy.POST;
import ninja.jaxy.Path;

@Singleton
@Path("/api/profile")
public class ProfileController {

  @Inject
  private Datastore datastore;

  @GET
  @Path("")
  public Result getProfiles() {
    return Results.json().render(datastore.find(Profile.class).asList());
  }

  @POST
  @Path("")
  public Result addProfile() {
    Profile profile = new Profile();
    profile.setFirstname("Max");
    profile.setLastname("Mustermann");
    profile.setCareerStage("Manager");
    datastore.save(profile);
    return Results.status(201).json().render(profile);
  }
}
