package controllers.api;

import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import exception.ElementNotFoundException;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Date;

import model.Resource;

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.exceptions.BadRequestException;
import ninja.jaxy.GET;
import ninja.jaxy.POST;
import ninja.jaxy.Path;
import ninja.params.PathParam;

@Singleton
@Path("/api/resource")
public class ResourceController {

  Logger LOG = LoggerFactory.getLogger(ResourceController.class);

  @Inject
  private Datastore datastore;

  @POST
  @Path("/upload")
  public Result upload(Context context) throws Exception {

    // Make sure the context really is a multipart context...
    if (context.isMultipart()) {
      // This is the iterator we can use to iterate over the
      // contents of the request.
      FileItemIterator fileItemIterator = context.getFileItemIterator();

      while (fileItemIterator.hasNext()) {
        FileItemStream item = fileItemIterator.next();

        String name = item.getFieldName();
        String contentType = item.getContentType();
        InputStream stream = item.openStream();

        LOG.error("name: {}", name);
        LOG.error("type:  {}", contentType);

        if (item.isFormField()) {
          LOG.warn("no form field expected");
        } else {
          Resource resource = new Resource();
          resource.setName(name);
          resource.setMimeType(contentType);
          resource.setContent(ByteStreams.toByteArray(stream));
          resource.setUploadTime(new Date());
          datastore.save(resource);
          return Results.ok().json().render(resource);
        }
      }
    }
    return Results.badRequest();
  }

  @Path("/{id}")
  @GET
  public Result getResourceById(@PathParam("id") String id) {
    if (Strings.isNullOrEmpty(id)) {
      throw new BadRequestException();
    }
    if (!ObjectId.isValid(id)) {
      throw new ElementNotFoundException();
    }

    final Resource resource = datastore.get(Resource.class, new ObjectId(id));
    if (resource == null) {
      throw new ElementNotFoundException();
    }
    return Results.json().render(resource);
  }
}
