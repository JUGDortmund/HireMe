package doctester;

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

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Lukas Eichler
 */
public class ProfileExportControllerTest extends NinjaDocTester {
  
  public static final String API_REQUEST_PATH = "/api/exportProfile/";
  private Profile profile;

  @Before
  public void setUp() throws Exception {
    profile = new Profile();
    profile.setFirstname("Klaus");
    profile.setLastname("Mustermann");
    getInjector().getInstance(Datastore.class).save(profile);
  }

  @Test
  public void exportProfileReturnsPDFDownload() throws Exception {
    final Response response = sayAndMakeRequest(Request.GET().url(testServerUrl().path(API_REQUEST_PATH + profile.getId())));

    PDFParser
      parser =
      new PDFParser(new ByteArrayInputStream(response.payload.getBytes("ISO-8859-1")));
    parser.parse();
    PDFTextStripper stripper = new PDFTextStripper();
    assertThat(stripper.getText(parser.getPDDocument())).contains("Klaus").contains("Mustermann");
  }

  @Test
  public void exportProfileHasStatus200() throws Exception {
    final Response response = sayAndMakeRequest(Request.GET().url(testServerUrl().path(API_REQUEST_PATH + profile.getId())));

    assertThat(response.httpStatus).isEqualTo(200);
  }
  
  @Test
  public void exportProfileWithoutIdHasStatus400() throws Exception {
    final Response response = sayAndMakeRequest(Request.GET().url(testServerUrl().path(API_REQUEST_PATH)));
    assertThat(response.httpStatus).isEqualTo(400);
  }
  
  @Test
  public void exportProfileWithInvalidIdHasStatus400() throws Exception {
    final Response response = sayAndMakeRequest(Request.GET().url(testServerUrl().path(API_REQUEST_PATH + 400)));
    assertThat(response.httpStatus).isEqualTo(400);
  }

  @Test
  public void exportProfileWithValidButNotUsedIdHasStatus404() throws Exception {
    final Response response = sayAndMakeRequest(Request.GET().url(testServerUrl().path(API_REQUEST_PATH + new ObjectId().toString())));
    assertThat(response.httpStatus).isEqualTo(404);
  }
}
