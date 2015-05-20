package doctester;

import com.fasterxml.jackson.core.type.TypeReference;

import org.bson.types.ObjectId;
import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;

import java.util.List;

import model.Project;
import ninja.NinjaDocTester;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectTest extends NinjaDocTester {

  TypeReference<List<Project>> PROJECT_LIST_TYPE = new TypeReference<List<Project>>() {
  };


  @Test
  public void addProjectReturns201() throws Exception {
    Response response = addNewProject();
    assertThat(response.httpStatus).isEqualTo(201);
  }

  @Test
  public void addProjectReturnsProject() throws Exception {
    Response response = addNewProject();
    assertThat(response.payloadAs(Project.class).getTitle()).isEqualTo("new Project");
  }

  @Test
  public void addProjectContainsId() throws Exception {
    Response response = addNewProject();
    assertThat(response.payloadAs(Project.class).getId()).isNotNull();
  }

  @Test
  public void getReturnsListOfProjects() throws Exception {
    Response response = getAllProjects();
    assertThat(response.payloadJsonAs(PROJECT_LIST_TYPE)).isNotNull();
  }

  @Test
  public void addingAProjectIncreasesSizeOfProjectList() throws Exception {
    Response firstResponse = getAllProjects();
    int oldSize = firstResponse.payloadJsonAs(PROJECT_LIST_TYPE).size();

    addNewProject();
    Response secondResponse = getAllProjects();

    assertThat(oldSize + 1).isEqualTo(secondResponse.payloadJsonAs(PROJECT_LIST_TYPE).size());
  }

  @Test
  public void getSingleProject() throws Exception {
    Project createdProject = addNewProject().payloadJsonAs(Project.class);
    Project singleProject = getSingleProject(createdProject.getId()
                                                 .toString()).payloadJsonAs(Project.class);
    assertThat(singleProject).isEqualTo(createdProject);
  }

  @Test
  public void getSingleProjectWithoutIdReturns400() throws Exception {
    Response response = getSingleProject("");
    assertThat(response.httpStatus).isEqualTo(400);
  }

  @Test
  public void getSingleProjectWithInvalidIdReturns404() throws Exception {
    Response response = getSingleProject("test");
    assertThat(response.httpStatus).isEqualTo(404);
  }

  @Test
  public void getSingleProjectWithValidButNotPersistedIdReturns404() {
    Response response = getSingleProject(new ObjectId().toString());
    assertThat(response.httpStatus).isEqualTo(404);
  }

  @Test
  public void successfullySaveProjectReturns200() throws Exception {
    final Project project = new Project();
    Response response = saveProject(project);
    assertThat(response.httpStatus).isEqualTo(200);
  }

  @Test
  public void successfullySaveProjectReturnsSavedProject() throws Exception {
    final Project project = addNewProject().payloadJsonAs(Project.class);
    project.setTitle("Test");
    Response response = saveProject(project);
    assertThat(response.payloadJsonAs(Project.class)).isEqualTo(project);
  }

  @Test
  public void saveWithoutProjectReturns400() throws Exception {
    Response response = saveProject(null);
    assertThat(response.httpStatus).isEqualTo(400);
  }

  @Test
  public void successfullyDeleteProjectReturns200() throws Exception {
    Project project = addNewProject().payloadJsonAs(Project.class);
    Response response = removeProject(project.getId());
    assertThat(response.httpStatus).isEqualTo(200);
  }

  @Test
  public void deleteWithoutReturns400() throws Exception {
    Response response = sayAndMakeRequest(Request.DELETE()
                                              .url(testServerUrl().path("/api/project")));
    assertThat(response.httpStatus).isEqualTo(400);
  }

  @Test
  public void deleteWithValidButUnknownIdReturns404() throws Exception {
    Response response = removeProject(new ObjectId());
    assertThat(response.httpStatus).isEqualTo(404);
  }

  private Response getSingleProject(final String id) {
    return sayAndMakeRequest(Request.GET().url(testServerUrl().path("/api/project/" + id)));
  }

  private Response getAllProjects() {
    return sayAndMakeRequest(Request.GET().url(testServerUrl().path("/api/project")));
  }

  private Response addNewProject() {
    return sayAndMakeRequest(Request.POST().url(testServerUrl().path("/api/project")));
  }

  private Response saveProject(Project project) {
    if (project == null) {
      return sayAndMakeRequest(Request.PUT()
                                   .url(testServerUrl().path("/api/project/null"))
                                   .payload(project)
                                   .contentTypeApplicationJson());
    }
    return sayAndMakeRequest(Request.PUT()
                                 .url(testServerUrl().path("/api/project/" + project.getId()))
                                 .payload(project)
                                 .contentTypeApplicationJson());

  }

  private Response removeProject(ObjectId id) {
    return sayAndMakeRequest(Request.DELETE().url(testServerUrl().path("/api/project/"
                                                                       + id.toString())));
  }
}