package doctester;

import com.fasterxml.jackson.core.type.TypeReference;

import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.doctester.testbrowser.Url;
import org.junit.Test;

import java.util.List;

import model.Project;
import ninja.NinjaDocTester;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectControllerTest extends NinjaDocTester {

  public static final TypeReference<List<Project>>
      PROJECT_LIST =
      new TypeReference<List<Project>>() {
      };

  @Test
  public void getProfilesReturnsListOfProfiles() throws Exception {
    Response response = sayAndMakeRequest(Request.GET().url(queryUrl()));
    assertThat(response.payloadJsonAs(PROJECT_LIST)).isEmpty();
  }

  @Test
  public void addProjectIncreasesTotalProjectSize() throws Exception {
    int oldSize = sayAndMakeRequest(Request.GET().url(queryUrl())).payloadJsonAs(PROJECT_LIST)
                      .size();
    sayAndMakeRequest(Request.POST().url(queryUrl()));
    assertThat(sayAndMakeRequest(Request.GET().url(queryUrl())).payloadJsonAs(PROJECT_LIST)
                   .size()).isEqualTo(oldSize + 1);
  }

  @Test
  public void addProjectReturnsAddedProject() throws Exception {
    Response response = sayAndMakeRequest(Request.POST().url(queryUrl()));
    assertThat(response.payloadAs(Project.class)).isNotNull();
  }

  private Url queryUrl() {
    return testServerUrl().path("/api/project/");
  }
}