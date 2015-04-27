package doctester;

import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;

import ninja.NinjaDocTester;

import static org.assertj.core.api.Assertions.assertThat;

public class RoutingTest extends NinjaDocTester {

  @Test
  public void defaultToIndex() throws Exception {
    Response response = sayAndMakeRequest(
        Request.GET().url(
            testServerUrl().path("/test")
        )
    );
    assertThat(response.payload).contains("<html ng-app=\"app\" lang=\"en\"");
  }

  @Test
  public void getTemplate() throws Exception {
    Response response = sayAndMakeRequest(
        Request.GET().url(
            testServerUrl().path("dashboard/dashboard.tpl.html")
        )
    );
    assertThat(response.payload).contains("<div class=\"box box-solid\" ng-controller=\"SearchController\">");
  }

  @Test
  public void apiCallReturns404Error() throws Exception {
    Response response = sayAndMakeRequest(
        Request.GET().url(
            testServerUrl().path("/api/testcall")
        )
    );
    assertThat(response.httpStatus).isEqualTo(404);
  }
}