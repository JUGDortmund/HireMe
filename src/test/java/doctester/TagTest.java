package doctester;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.core.type.TypeReference;

import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;

import java.util.List;

import dtos.TagList;
import model.Profile;
import ninja.NinjaDocTester;

import static org.assertj.core.api.Assertions.assertThat;

public class TagTest extends NinjaDocTester {

  public static final TypeReference<List<TagList>>
      TYPE_REFERENCE = new TypeReference<List<TagList>>() {
      };


  @Test
  public void getReturnsObjectOfLists() throws Exception {
    Profile testProfile = new Profile();
    testProfile.setIndustries(Lists.newArrayList("testIndustry"));
    testProfile.setDegrees(Lists.newArrayList("Bachelor", "Master"));
    saveProfile(testProfile);

    Response response = sayAndMakeRequest(Request.GET().url(testServerUrl().path("/api/tags")));
    List<TagList> result = response.payloadJsonAs(TYPE_REFERENCE);
    assertThat(result.get(0).getValues()).isEqualTo(Lists.newArrayList("Bachelor", "Master"));
    assertThat(result.get(1).getValues()).isEqualTo(Lists.newArrayList("testIndustry"));
  }

  private Response saveProfile(Profile profile) {
    return sayAndMakeRequest(Request.PUT()
                                 .url(testServerUrl().path("/api/profile/" + profile.getId()))
                                 .payload(profile)
                                 .contentTypeApplicationJson());

  }
}