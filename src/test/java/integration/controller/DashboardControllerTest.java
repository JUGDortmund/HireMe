package integration.controller;

import ninja.NinjaTest;

import org.junit.Before;

public class DashboardControllerTest extends NinjaTest {

  @Before
  public void setup() {
    ninjaTestBrowser.makeRequest(getServerAddress() + "setup");
  }
}
