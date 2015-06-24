package controllers.api;

import com.google.common.base.Strings;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import exception.ElementNotFoundException;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import model.Profile;
import model.Project;
import model.events.EntityChangedEvent;

import ninja.Result;
import ninja.Results;
import ninja.exceptions.BadRequestException;
import ninja.jaxy.DELETE;
import ninja.jaxy.GET;
import ninja.jaxy.POST;
import ninja.jaxy.PUT;
import ninja.jaxy.Path;
import ninja.params.PathParam;

@Singleton
@Path("/api/project")
public class ProjectController {

  private static final String DEFAULT_PROJECT_NAME = "neues Projekt";

  @Inject
  private Datastore datastore;

  @Inject
  private EventBus eventBus;

  @Path("")
  @GET
  public Result getAllProjects() {
    return Results.json().render(datastore.find(Project.class).asList());
  }

  @Path("/{id}")
  @GET
  public Result getSingleProjectById(@NotNull @PathParam("id") final String id) {

    if (Strings.isNullOrEmpty(id) || !ObjectId.isValid(id)) {
      throw new BadRequestException();
    }
    final Project project = datastore.get(Project.class, new ObjectId(id));
    if (project == null) {
      throw new ElementNotFoundException();
    }
    return Results.json().render(project);
  }

  @Path("/{id}")
  @PUT
  public Result saveProject(@NotNull final Project project) {
    if (project == null) {
      throw new BadRequestException();
    }
    datastore.save(project);
    eventBus.post(new EntityChangedEvent<>(project));
    return Results.json().render(project);
  }

  @Path("")
  @POST
  public Result addProject() {
    Project project = new Project();
    project.setTitle(DEFAULT_PROJECT_NAME);
    datastore.save(project);
    return Results.status(Result.SC_201_CREATED).json().render(project);
  }

  @Path({"/{id}", ""})
  @DELETE
  public Result deleteProject(@NotNull @PathParam("id") final String id) {
    if (Strings.isNullOrEmpty(id) || !ObjectId.isValid(id)) {
      throw new BadRequestException();
    }
    final Project project = datastore.get(Project.class, new ObjectId(id));
    if (project == null) {
      throw new ElementNotFoundException();
    }
    List<Profile> referencedProfiles =
        datastore
            .find(Profile.class)
            .asList()
            .stream()
            .filter(
                x -> x.getProjectAssociations() != null
                    && x.getProjectAssociations().stream()
                        .anyMatch(y -> y.getProject() != null && y.getProject().equals(project)))
            .collect(Collectors.toList());
    if (referencedProfiles.size() > 0) {
      return Results.status(450).json().render(referencedProfiles);
    }
    datastore.delete(project);
    return Results.ok().json();
  }
}
