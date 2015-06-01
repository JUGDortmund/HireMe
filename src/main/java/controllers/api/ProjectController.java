package controllers.api;

import com.google.common.base.Strings;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import exception.ElementNotFoundException;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import javax.validation.constraints.NotNull;

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

    if (Strings.isNullOrEmpty(id)) {
      throw new BadRequestException();
    }
    if (!ObjectId.isValid(id)) {
      throw new ElementNotFoundException();
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
    project.setTitle("new Project");
    datastore.save(project);
    return Results.status(Result.SC_201_CREATED).json().render(project);
  }

  @Path({"/{id}", ""})
  @DELETE
  public Result deleteProject(@NotNull @PathParam("id") final String id) {
    if (Strings.isNullOrEmpty(id)) {
      throw new BadRequestException();
    }
    final Project project = datastore.get(Project.class, new ObjectId(id));
    if (project == null) {
      throw new ElementNotFoundException();
    }
    datastore.delete(project);
    return Results.ok().json();
  }
}
