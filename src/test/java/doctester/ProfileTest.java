package doctester;

import com.fasterxml.jackson.core.type.TypeReference;

import org.bson.types.ObjectId;
import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;

import java.util.List;

import model.Profile;
import ninja.NinjaDocTester;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileTest extends NinjaDocTester {

  public static final TypeReference<List<Profile>> PROFILE_LIST_TYPE =
      new TypeReference<List<Profile>>() {
      };

  @Test
  public void addProfileReturns201() throws Exception {
    Response response = addNewProfile();
    assertThat(response.httpStatus).isEqualTo(201);
  }

  @Test
  public void addProfileReturnsProfile() throws Exception {
    Response response = addNewProfile();
    assertThat(response.payloadAs(Profile.class).getFirstname()).isEqualTo("Max");
  }

  @Test
  public void addProfileContainsId() throws Exception {
    Response response = addNewProfile();
    assertThat(response.payloadAs(Profile.class).getId()).isNotNull();
  }

  @Test
  public void getReturnsListOfProfiles() throws Exception {
    Response response = getAllProfiles();
    assertThat(response.payloadJsonAs(PROFILE_LIST_TYPE)).isNotNull();
  }

  @Test
  public void addingAProfileIncreasesSizeOfProfileList() throws Exception {
    Response firstResponse = getAllProfiles();
    int oldSize = firstResponse.payloadJsonAs(PROFILE_LIST_TYPE).size();

    addNewProfile();
    Response secondResponse = getAllProfiles();

    assertThat(oldSize + 1).isEqualTo(secondResponse.payloadJsonAs(PROFILE_LIST_TYPE).size());
  }

  @Test
  public void getSingleProfile() throws Exception {
    Profile createdProfile = addNewProfile().payloadJsonAs(Profile.class);
    Profile singleProfile = getSingleProfile(createdProfile.getId()
                                                 .toString()).payloadJsonAs(Profile.class);
    assertThat(singleProfile).isEqualTo(createdProfile);
  }

  @Test
  public void getSingleProfileWithoutIdReturns400() throws Exception {
    Response response = getSingleProfile("");
    assertThat(response.httpStatus).isEqualTo(400);
  }

  @Test
  public void getSingleProfileWithInvalidIdReturns404() throws Exception {
    Response response = getSingleProfile("test");
    assertThat(response.httpStatus).isEqualTo(404);
  }

  @Test
  public void getSingleProfileWithValidButNotPersistedIdReturns404() {
    Response response = getSingleProfile(new ObjectId().toString());
    assertThat(response.httpStatus).isEqualTo(404);
  }

  @Test
  public void successfullySaveProfileReturns200() throws Exception {
    final Profile profile = new Profile();
    Response response = sayAndMakeRequest(Request.POST()
                                              .url(testServerUrl().path("/api/profile"))
                                              .payload(profile));
    assertThat(response.httpStatus).isEqualTo(200);
  }

  @Test
  public void successfullySaveProfileReturnsSavedProfile() throws Exception {
    final Profile profile = new Profile();
    profile.setFirstname("Max");
    Response response = sayAndMakeRequest(Request.POST()
                                              .url(testServerUrl().path("/api/profile"))
                                              .payload(profile));
    assertThat(response.payloadJsonAs(PROFILE_LIST_TYPE)).isEqualTo(profile);
  }

  @Test
  public void saveWithoutProfileReturns400() throws Exception {
    Response response = sayAndMakeRequest(Request.POST().url(testServerUrl().path("/api/profile")));
    assertThat(response.httpStatus).isEqualTo(400);
  }

  private Response getSingleProfile(final String id) {
    return sayAndMakeRequest(Request.GET().url(testServerUrl().path("/api/profile/" + id)));
  }

  private Response getAllProfiles() {
    return sayAndMakeRequest(Request.GET().url(testServerUrl().path("/api/profile")));
  }

  private Response addNewProfile() {
    return sayAndMakeRequest(Request.POST().url(testServerUrl().path("/api/profile")));
  }
}
