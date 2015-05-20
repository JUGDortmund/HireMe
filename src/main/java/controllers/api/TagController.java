package controllers.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import ninja.Result;
import ninja.Results;
import ninja.jaxy.GET;
import ninja.jaxy.Path;
import services.TagService;

@Singleton
@Path("/api/tags")
public class TagController {

  @Inject
  private TagService tagService;


  @Path("")
  @GET
  public Result getAllTags() {
    return Results.json().render(tagService.getTags());
  }
}
