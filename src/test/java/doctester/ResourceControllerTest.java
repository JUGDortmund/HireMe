package doctester;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.StringContains.containsString;

import com.google.common.io.Files;

import org.doctester.testbrowser.HttpConstants;
import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import model.BaseModel;

import ninja.NinjaDocTester;

public class ResourceControllerTest extends NinjaDocTester {

  private static final String RESOURCE_CONTROLLER_BASE_URL = "/api/resource/";
  private static final String POST_UPLOAD_RESOURCE_URL = RESOURCE_CONTROLLER_BASE_URL + "upload";
  private static final String GET_RESOURCE_URL = RESOURCE_CONTROLLER_BASE_URL + "{id}/{format}";
  private static final String THUMBNAIL_FORMAT = "thumbnail";
  private static final String ORIGINAL_FORMAT = "original";
  private static final String CONTENT_TYPE_PNG = "image/png";

  private URL url;
  private File file;

  @Before
  public void setup() throws URISyntaxException {
    url = this.getClass().getResource("/fileupload.png");
    file = new File(url.toURI());
  }

  @Test
  public void testResourceUpload() throws URISyntaxException {
    sayNextSection("Upload a resource");
    say("Uploading a resource is a POST request to " + POST_UPLOAD_RESOURCE_URL);

    Response response =
        sayAndMakeRequest(Request.POST().url(testServerUrl().path(POST_UPLOAD_RESOURCE_URL))
            .addFileToUpload("testfile", file));
    BaseModel baseModel = response.payloadJsonAs(BaseModel.class);

    sayAndAssertThat("We get back a json containing the id of the uploaded resource",
        baseModel.getId(), notNullValue());
  }

  @Test
  public void testGetResourceInOriginalFormat() throws URISyntaxException, IOException {
    sayNextSection("Download a resource in " + ORIGINAL_FORMAT + " format");
    say("On upload of a file the resource id of the file is returned.");
    Response uploadResponse = uploadFile(file);
    BaseModel baseModel = uploadResponse.payloadJsonAs(BaseModel.class);
    String id = baseModel.getId().toString();

    say("Downloading a resource in " + ORIGINAL_FORMAT + " fileformat is a GET request to "
        + GET_RESOURCE_URL);
    Response response =
        sayAndMakeRequest(Request.GET().url(
            testServerUrl().path(
                GET_RESOURCE_URL.replace("{id}", id).replace("{format}", ORIGINAL_FORMAT))));

    String content = response.payload;
    String expected = Files.toString(file, Charset.defaultCharset());

    sayAndAssertThat("We get the resource back in " + ORIGINAL_FORMAT + " format", content,
        equalTo(expected));
  }

  @Test
  public void testGetResourceInThumbnailFormat() throws URISyntaxException, IOException {
    sayNextSection("Download a resource in " + THUMBNAIL_FORMAT + " format");
    say("On upload of a file the resource id of the file is returned.");
    Response uploadResponse = uploadFile(file);
    BaseModel baseModel = uploadResponse.payloadJsonAs(BaseModel.class);
    String id = baseModel.getId().toString();

    say("Downloading a resource in " + THUMBNAIL_FORMAT + " fileformat is a GET request to "
        + GET_RESOURCE_URL);
    Response response =
        sayAndMakeRequest(Request.GET().url(
            testServerUrl().path(
                GET_RESOURCE_URL.replace("{id}", id).replace("{format}", THUMBNAIL_FORMAT))));
    String contentType = response.headers.get(HttpConstants.HEADER_CONTENT_TYPE);

    sayAndAssertThat("We get the resource back in " + THUMBNAIL_FORMAT + " format",
        response.payload, containsString("PNG"));
    sayAndAssertThat("We get a response header with png content type", contentType,
        containsString(CONTENT_TYPE_PNG));

  }

  private Response uploadFile(File file) throws URISyntaxException {

    Response response =
        sayAndMakeRequest(Request.POST().url(testServerUrl().path(POST_UPLOAD_RESOURCE_URL))
            .addFileToUpload("testfile", file));

    return response;
  }
}
