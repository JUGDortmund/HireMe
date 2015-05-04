package controllers.api;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import model.Profile;
import ninja.Result;
import ninja.Results;
import ninja.exceptions.BadRequestException;
import ninja.jaxy.GET;
import ninja.jaxy.POST;
import ninja.jaxy.Path;
import ninja.params.PathParam;

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

  @Path("/{id}")
  @GET
  public Result getSingleProfileById(@PathParam("id") String id) {
    if (Strings.isNullOrEmpty(id)) {
      throw new BadRequestException();
    }
    if (!ObjectId.isValid(id)) {
      return Results.json().status(404);
    }

    final Profile profile = datastore.get(Profile.class, new ObjectId(id));
    if (profile == null) {
      return Results.json().status(404);
    }

    return Results.json().render(profile);
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
