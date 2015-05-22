package doctester;

import static org.assertj.core.api.Assertions.assertThat;

import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import model.BaseModel;

import ninja.NinjaDocTester;

public class ResourceControllerTest extends NinjaDocTester {

  String RESOURCE_CONTROLLER_BASE_URL = "/api/resource/";
  String POST_UPLOAD_RESOURCE_URL = RESOURCE_CONTROLLER_BASE_URL + "upload";
  String GET_RESOURCE_URL = RESOURCE_CONTROLLER_BASE_URL + "/{id}/{format}";

  @Test
  public void testResourceUpload() throws URISyntaxException {
    sayNextSection("Upload a resource");
    say("Uploading a resource is a POST request to " + POST_UPLOAD_RESOURCE_URL);

    Response response = uploadFile();

    BaseModel baseModel = response.payloadJsonAs(BaseModel.class);

    assertThat(baseModel.getId()).isNotNull();
    // sayAndAssertThat("We get back an json containing the id of the uploaded resource",
    // baseModel.getId(), )
  }

  private Response uploadFile() throws URISyntaxException {
    URL url = this.getClass().getResource("/fileupload.png");
    File file = new File(url.toURI());

    Response response =
        sayAndMakeRequest(Request.POST().url(testServerUrl().path(POST_UPLOAD_RESOURCE_URL))
            .addFileToUpload("testfile", file));

    return response;
  }

  @Test
  public void testGetResourceInOriginalFormat() {

  }

  // @Test
  // public void testGetAndPostArticleViaJson() throws Exception {
  //
  // sayNextSection("Retrieving articles for a user (Json)");
  //
  // say("Retrieving all articles of a user is a GET request to " + GET_ARTICLES_URL);
  //
  // Response response = sayAndMakeRequest(
  // Request.GET().url(
  // testServerUrl().path(GET_ARTICLES_URL.replace("{username}", "bob@gmail.com"))));
  //
  // ArticlesDto articlesDto = response.payloadAs(ArticlesDto.class);
  //
  // sayAndAssertThat("We get back all 3 articles of that user",
  // articlesDto.articles.size(),
  // is(3));
  //
  // }

}
