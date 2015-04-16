package integration.controller;

import ninja.NinjaTest;

import org.junit.Test;
import org.junit.Before;

import static org.assertj.core.api.Assertions.assertThat;

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
