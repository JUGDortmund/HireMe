package controllers.api;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import exception.ElementNotFoundException;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.ResourceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.validation.constraints.NotNull;

import model.Resource;

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.exceptions.BadRequestException;
import ninja.jaxy.GET;
import ninja.jaxy.POST;
import ninja.jaxy.Path;
import ninja.params.PathParam;
import ninja.utils.HttpCacheToolkit;

@Singleton
@Path("/api/resource")
public class ResourceController {

  Logger LOG = LoggerFactory.getLogger(ResourceController.class);

  @Inject
  private Datastore datastore;

  @Inject
  private HttpCacheToolkit httpCacheToolkit;

  @POST
  @Path("/upload")
  public Result uploadResource(Context context) throws IOException, FileUploadException {

    if (!context.isMultipart()) {
      throw new BadRequestException();
    }

    FileItemIterator fileItemIterator = context.getFileItemIterator();

    if (!(fileItemIterator != null && fileItemIterator.hasNext())) {
      throw new BadRequestException();
    }

    FileItemStream item = fileItemIterator.next();

    if (item.isFormField()) {
      throw new BadRequestException("no form field expected");
    }

    return processUploadStream(item);
  }

  private Result processUploadStream(@NotNull FileItemStream item) throws IOException {
    Preconditions.checkNotNull(item);

    // collect information
    String name = item.getFieldName();
    String contentType = item.getContentType();
    byte[] content;
    try (InputStream stream = item.openStream()) {
      content = ByteStreams.toByteArray(stream);
    }

    // create resource
    Resource resource = new Resource();
    resource.setName(name);
    resource.setMimeType(contentType);
    resource.setContent(content);
    resource.setLastModified(new Date());
    datastore.save(resource);

    // create thumbnail
    Resource thumbnail = ResourceUtil.createThumbnail(resource);
    datastore.save(thumbnail);
    resource.setThumbnail(thumbnail);
    datastore.save(resource);

    return Results.ok().json().render("id", resource.getId().toString());
  }

  @Path("/{id}/{format}")
  @GET
  public Result getResourceByIdAndFormat(Context context, @PathParam("id") String id,
      @PathParam("format") String format) {
    if (Strings.isNullOrEmpty(id) || Strings.isNullOrEmpty(format)) {
      throw new BadRequestException();
    }
    if (!ObjectId.isValid(id)) {
      throw new ElementNotFoundException();
    }

    final Resource resource = datastore.get(Resource.class, new ObjectId(id));
    if (resource == null) {
      throw new ElementNotFoundException();
    }

    Result result;

    switch (format.toLowerCase()) {
      case "thumbnail":
        result = createCachedResourceResult(context, resource.getThumbnail());
        break;
      case "original":
        result = createCachedResourceResult(context, resource);
        break;
      default:
        throw new BadRequestException();
    }

    return result;
  }

  private Result createCachedResourceResult(Context context, Resource resource) {
    Result result = Results.ok().json();

    httpCacheToolkit.addEtag(context, result, resource.getLastModified().getTime());

    // if resource was modified render image as result
    if (result.getStatusCode() != Result.SC_304_NOT_MODIFIED) {
      result.contentType(resource.getMimeType()).renderRaw(resource.getContent());
    }

    // if resource was not modified return result with not modified header
    return result;
  }
}
