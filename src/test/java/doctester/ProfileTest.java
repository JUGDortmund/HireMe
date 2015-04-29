package doctester;

import com.fasterxml.jackson.core.type.TypeReference;

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
    Response response = sayAndMakeRequest(Request.POST().url(testServerUrl().path("/api/profile")));
    assertThat(response.httpStatus).isEqualTo(201);
  }

  @Test
  public void addProfileReturnsProfile() throws Exception {
    Response response = sayAndMakeRequest(Request.POST().url(testServerUrl().path("/api/profile")));
    assertThat(response.payloadAs(Profile.class).getFirstname()).isEqualTo("Max");
  }

  @Test
  public void getReturnsListOfProfiles() throws Exception {
    Response response = sayAndMakeRequest(Request.GET().url(testServerUrl().path("/api/profile")));
    assertThat(response.payloadJsonAs(PROFILE_LIST_TYPE)).isNotNull();
  }

  @Test
  public void addingAProfileIncreasesSizeOfProfileList() throws Exception {
    Response firstResponse =
        sayAndMakeRequest(Request.GET().url(testServerUrl().path("/api/profile")));
    int oldSize = firstResponse.payloadJsonAs(PROFILE_LIST_TYPE).size();

    sayAndMakeRequest(Request.POST().url(testServerUrl().path("/api/profile")));

    Response secondResponse =
        sayAndMakeRequest(Request.GET().url(testServerUrl().path("/api/profile")));

    assertThat(oldSize).isLessThan(secondResponse.payloadJsonAs(PROFILE_LIST_TYPE).size());
  }
}
