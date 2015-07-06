package doctester;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;

import model.ExportTemplateDefinition;
import model.Profile;
import ninja.NinjaDocTester;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.bson.types.ObjectId;
import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

/**
 * @author Lukas Eichler
 */
public class ProfileExportControllerTest extends NinjaDocTester {

  public static final String API_REQUEST_PATH = "/api/exportProfile/";
  private Profile profile;
  private ExportTemplateDefinition template;

  @Before
  public void setUp() throws Exception {
    profile = new Profile();
    profile.setFirstname("Klaus");
    profile.setLastname("Mustermann");
    getInjector().getInstance(Datastore.class).save(profile);
    template =
        new ExportTemplateDefinition("testTemplate", "template.ftl.html",
            "maredit_${firstname}_${lastname}_$today.pdf");
  }

  @Test
  public void exportProfileReturnsPDFDownload() throws Exception {
    final Response response =
        sayAndMakeRequest(Request.GET().url(
            testServerUrl().path(API_REQUEST_PATH + profile.getId() + "/" + template.getName())));

    PDFParser parser =
        new PDFParser(new ByteArrayInputStream(response.payload.getBytes("ISO-8859-1")));
    parser.parse();
    PDFTextStripper stripper = new PDFTextStripper();
    assertThat(stripper.getText(parser.getPDDocument())).contains("Klaus").contains("Mustermann");
  }

  @Test
  public void exportProfileHasStatus200() throws Exception {
    final Response response =
        sayAndMakeRequest(Request.GET().url(
            testServerUrl().path(API_REQUEST_PATH + profile.getId() + "/" + template.getName())));

    assertThat(response.httpStatus).isEqualTo(200);
  }

  @Test
  public void exportProfileWithoutIdHasStatus400() throws Exception {
    final Response response =
        sayAndMakeRequest(Request.GET().url(testServerUrl().path(API_REQUEST_PATH)));
    assertThat(response.httpStatus).isEqualTo(400);
  }

  @Test
  public void exportProfileWithInvalidIdHasStatus400() throws Exception {
    final Response response =
        sayAndMakeRequest(Request.GET().url(testServerUrl().path(API_REQUEST_PATH + 400)));
    assertThat(response.httpStatus).isEqualTo(400);
  }

  @Test
  public void exportProfileWithValidButNotUsedIdHasStatus404() throws Exception {
    final Response response =
        sayAndMakeRequest(Request.GET().url(
            testServerUrl().path(API_REQUEST_PATH + new ObjectId().toString())));
    assertThat(response.httpStatus).isEqualTo(404);
  }
}
