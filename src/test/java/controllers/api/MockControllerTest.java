package controllers.api;

import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.doctester.testbrowser.Url;
import org.junit.Test;

import ninja.NinjaDocTester;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Lukas Eichler
 */
public class MockControllerTest extends NinjaDocTester {

  private Url getBaseUrl() {
    return testServerUrl().path("api/mock");
  }

  @Test
  public void getMockReturns200() throws Exception {
    Response response = sayAndMakeRequest(Request.GET().url(getBaseUrl()));
    assertThat(response.httpStatus).isEqualTo(200);
  }

  @Test
  public void getNotFoundMockReturns404() throws Exception {
    Response response = sayAndMakeRequest(Request.GET().url(getBaseUrl().path("/notfound")));
    assertThat(response.httpStatus).isEqualTo(404);
  }

  @Test
  public void putMockReturns200() throws Exception {
    Response response = sayAndMakeRequest(Request.PUT().url(getBaseUrl()));
    assertThat(response.httpStatus).isEqualTo(200);
  }

  @Test
  public void putNotFoundMockReturns404() throws Exception {
    Response response = sayAndMakeRequest(Request.PUT().url(getBaseUrl().path("/notfound")));
    assertThat(response.httpStatus).isEqualTo(404);
  }

  @Test
  public void putNoContentMockReturns204() throws Exception {
    Response response = sayAndMakeRequest(Request.PUT().url(getBaseUrl().path("/nocontent")));
    assertThat(response.httpStatus).isEqualTo(204);
  }

  @Test
  public void postMockReturns200() throws Exception {
    Response response = sayAndMakeRequest(Request.POST().url(getBaseUrl()));
    assertThat(response.httpStatus).isEqualTo(201);
  }

  @Test
  public void postNotFoundMockReturns404() throws Exception {
    Response response = sayAndMakeRequest(Request.POST().url(getBaseUrl().path("/notfound")));
    assertThat(response.httpStatus).isEqualTo(404);
  }

  @Test
  public void deleteMockReturns200() throws Exception {
    Response response = sayAndMakeRequest(Request.PUT().url(getBaseUrl()));
    assertThat(response.httpStatus).isEqualTo(200);
  }

  @Test
  public void deleteNotFoundMockReturns404() throws Exception {
    Response response = sayAndMakeRequest(Request.DELETE().url(getBaseUrl().path("/notfound")));
    assertThat(response.httpStatus).isEqualTo(404);
  }
}