package controllers.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.mongodb.morphia.Datastore;

import model.Project;
import ninja.Result;
import ninja.Results;
import ninja.jaxy.GET;
import ninja.jaxy.POST;
import ninja.jaxy.Path;

@Singleton
@Path("/api/project")
public class ProjectController {

  @Inject
  private Datastore datastore;

  @GET
  @Path("/")
  public Result getProjects() {
    return Results.json().render(datastore.find(Project.class).asList());
  }

  @POST
  @Path("/")
  public Result addProject() {
    Project project = new Project();
    datastore.save(project);
    return Results.json().render(project);
  }
}
