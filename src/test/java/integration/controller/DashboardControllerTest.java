package integration.controller;

import static org.assertj.core.api.Assertions.assertThat;
import ninja.NinjaTest;

import org.junit.Before;
import org.junit.Test;

public class DashboardControllerTest extends NinjaTest {

  @Before
  public void setup() {
    ninjaTestBrowser.makeRequest(getServerAddress() + "setup");
  }

  @Test
  public void testDashboardContainsHalloWelt() throws Exception {
    String result = ninjaTestBrowser.makeRequest(getServerAddress() + "/");
    assertThat(result.contains("Hallo Welt"));
  }

}
