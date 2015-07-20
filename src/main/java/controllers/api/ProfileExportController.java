package controllers.api;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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
  private final String ANONYM_TEMPLATE = "AnonymTemplate";

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
    if (Strings.isNullOrEmpty(profileId) || !ObjectId.isValid(profileId)
        || Strings.isNullOrEmpty(template)) {
      throw new BadRequestException();
    }
    final Profile profile = datastore.get(Profile.class, new ObjectId(profileId));
    if (profile == null || exportTemplate.getTemplate(template) == null) {
      throw new ElementNotFoundException();
    }

    String[] pattern = exportTemplate.getTemplate(template).getFileNamePattern().split("_");
    final Map dataModel = exportService.transformModelToTemplateValues(profile);
    String name = "";
    for (String element : pattern) {
      if (!element.startsWith("$")) {
        name += element + "_";
      } else {
        if (element.contains("$today.pdf")) {
          name += DATE_FORMAT.format(new Date()) + ".pdf";
        } else {
          name +=
              dataModel.get(element.substring(element.indexOf("{") + 1, element.lastIndexOf("}")))
                  + "_";
        }
      }
    }
    final String PDFname = name.replaceAll("\\s", "_");

    return Results
        .ok()
        .contentType("application/pdf")
        .charset("ISO-8859-1")
        .addHeader("Content-Disposition", "attachment; filename=" + PDFname)
        .text()
        .render(
            new String(exportService.exportToPdf(profile, exportTemplate.getTemplate(template)),
                "ISO-8859-1"));
  }
}
