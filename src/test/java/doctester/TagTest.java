package doctester;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.core.type.TypeReference;

import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import model.Profile;
import ninja.NinjaDocTester;

import static org.assertj.core.api.Assertions.assertThat;

public class TagTest extends NinjaDocTester {

  public static final TypeReference<Map<String, List<String>>>
      TYPE_REFERENCE =
      new TypeReference<Map<String, List<String>>>() {
      };

  @Test
  public void getReturnsObjectOfLists() throws Exception {
    Profile testProfile = new Profile();
    testProfile.setIndustries(Lists.newArrayList("testIndustry"));
    testProfile.setDegrees(Lists.newArrayList("Bachelor", "Master"));
    saveProfile(testProfile);

    Response response = sayAndMakeRequest(Request.GET().url(testServerUrl().path("/api/tags")));
    Map<String, List<String>> result = response.payloadJsonAs(TYPE_REFERENCE);
    assertThat(result).containsOnlyKeys("industries", "degrees");
    assertThat(result.get("industries")).isEqualTo(Lists.newArrayList("testIndustry"));
    assertThat(result.get("degrees")).isEqualTo(Lists.newArrayList("Bachelor", "Master"));
  }

  private Response saveProfile(Profile profile) {
    return sayAndMakeRequest(Request.PUT()
                                 .url(testServerUrl().path("/api/profile/" + profile.getId()))
                                 .payload(profile)
                                 .contentTypeApplicationJson());

  }
}