package controllers.api;

import model.Profile;
import model.events.EntityChangedEvent;
import ninja.Result;
import ninja.Results;
import ninja.exceptions.BadRequestException;
import ninja.jaxy.GET;
import ninja.jaxy.POST;
import ninja.jaxy.PUT;
import ninja.jaxy.Path;
import ninja.params.PathParam;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import exception.ElementNotFoundException;

@Singleton
@Path("/api/profile")
public class ProfileController {

  @Inject
  private Datastore datastore;

  @Inject
  private EventBus eventBus;

  @Path("")
  @GET
  public Result getProfiles() {
    return Results.json().render(datastore.find(Profile.class).asList());
  }

  @Path("/{id}")
  @GET
  public Result getSingleProfileById(@PathParam("id") String id) {

    if (Strings.isNullOrEmpty(id) || !ObjectId.isValid(id)) {
      throw new BadRequestException();
    }

    final Profile profile = datastore.get(Profile.class, new ObjectId(id));
    if (profile == null) {
      throw new ElementNotFoundException();
    }
    return Results.json().render(profile);
  }

  @Path("")
  @POST
  public Result addProfile() {
    Profile profile = new Profile();
    profile.setFirstname("Max");
    profile.setLastname("Mustermann");
    profile.setCareerLevel(Lists.newArrayList("Manager"));
    profile.setFirstMainFocus("JavaEE Entwickler");
    profile.setSecondMainFocus("Hybris Zertifiziert");
    datastore.save(profile);
    return Results.status(Result.SC_201_CREATED).json().render(profile);
  }

  @Path("/{id}")
  @PUT
  public Result saveProfile(Profile profile) {
    if (profile == null) {
      throw new BadRequestException();
    }
    if (profile.getProjectAssociations() != null) {
      profile.getProjectAssociations().forEach(datastore::save);
    }
    datastore.save(profile);
    eventBus.post(new EntityChangedEvent<>(profile));
    return Results.json().render(profile);
  }

}
