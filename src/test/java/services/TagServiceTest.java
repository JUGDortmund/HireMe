package services;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.Before;
import org.junit.Test;

import conf.Module;
import model.Profile;
import model.events.EntityChangedEvent;
import util.TestModule;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Lukas Eichler
 */
public class TagServiceTest {

  private TagService service;
  private Profile profile;

  @Before
  public void setUp() throws Exception {
    service = new TagService();
    profile = new Profile();
    profile.setDegrees(Lists.newArrayList("Master", "Bachelor", "Bachelor"));
    profile.setCareerStage(Lists.newArrayList("Associate"));
    service.add(profile);
  }

  @Test
  public void noDuplicates() throws Exception {
    assertThat(service.getTags("degrees")).hasSize(2);
  }

  @Test
  public void alphabeticallySorted() throws Exception {
    assertThat(service.getTags("degrees")).startsWith("Bachelor");
  }

  @Test
  public void differentTagsAreSavedInDifferentSets() throws Exception {
    assertThat(service.getTags("careerStage")).contains("Associate").doesNotContain("Bachelor");
    assertThat(service.getTags("degrees")).contains("Bachelor").doesNotContain("Associate");
  }

  @Test
  public void getTagByName() throws Exception {
    assertThat(service.getTags("careerStage")).contains("Associate");
  }

  @Test
  public void addTagsByEvent() throws Exception {
    Injector injector = Guice.createInjector(new TestModule(), new Module());
    service = injector.getInstance(TagService.class);
    Profile changedProfile = new Profile();
    changedProfile.setIndustries(Lists.newArrayList("TestIndustry"));
    injector.getInstance(EventBus.class).post(new EntityChangedEvent<>(changedProfile));

    assertThat(service.getTags("industries")).contains("TestIndustry");
  }
}