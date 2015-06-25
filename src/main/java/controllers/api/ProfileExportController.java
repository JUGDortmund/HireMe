package controllers.api;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;

import model.Profile;
import ninja.Result;
import ninja.Results;
import ninja.exceptions.BadRequestException;
import ninja.jaxy.GET;
import ninja.jaxy.Path;
import ninja.params.PathParam;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import services.ProfileExportService;
import services.TemplateExportService;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import exception.ElementNotFoundException;

/**
 * @author Lukas Eichler
 */
@Singleton
@Path("(?i)/api/exportProfile")
public class ProfileExportController {

  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd");
  private final Datastore datastore;
  private final ProfileExportService exportService;
  private final TemplateExportService exportTemplate;

  @Inject
  public ProfileExportController(Datastore datastore, ProfileExportService exportService,
      TemplateExportService exportTemplate) {
    this.datastore = datastore;
    this.exportService = exportService;
    this.exportTemplate = exportTemplate;
  }

  @Path("/{profileId}/{templateName}")
  @GET
  public Result exportProfile(@NotNull @PathParam("profileId") final String profileId,
      @PathParam("templateName") final String template) throws IOException {
    if (Strings.isNullOrEmpty(profileId) || !ObjectId.isValid(profileId)) {
      throw new BadRequestException();
    }
    final Profile profile = datastore.get(Profile.class, new ObjectId(profileId));
    if (profile == null) {
      throw new ElementNotFoundException();
    }

    final String name =
        "maredit_" + profile.getFirstname() + "_" + profile.getLastname() + "_"
            + DATE_FORMAT.format(new Date()) + ".pdf";

    return Results
        .ok()
        .contentType("application/pdf")
        .charset("ISO-8859-1")
        .addHeader("Content-Disposition", "attachment; filename=" + name)
        .text()
        .render(
            new String(exportService.exportToPdf(profile, exportTemplate.getTemplate(template)),
                "ISO-8859-1"));
  }
}
