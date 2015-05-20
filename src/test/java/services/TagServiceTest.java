package services;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.inject.Guice;
import com.google.inject.Injector;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import conf.Module;
import dtos.TagList;
import model.Profile;
import model.events.EntityChangedEvent;
import model.events.EntityRemovedEvent;
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
    profile.setCareerLevel(Lists.newArrayList("Associate"));
    profile.setId(new ObjectId());
    service.add(profile);
  }

  @Test(expected = NullPointerException.class)
  public void addNullThrowsException() throws Exception {
    service.remove(null);
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
    assertThat(service.getTags("careerLevel")).contains("Associate").doesNotContain("Bachelor");
    assertThat(service.getTags("degrees")).contains("Bachelor").doesNotContain("Associate");
  }

  @Test
  public void getTagByName() throws Exception {
    assertThat(service.getTags("careerLevel")).contains("Associate");
  }

  @Test
  public void getAllTags() throws Exception {
    List<TagList> tags = service.getTags();
    assertThat(tags.get(0).getValues()).contains("Bachelor", "Master");
    assertThat(tags.get(0).getName()).isEqualTo("degrees");
  }

  @Test
  public void removeTagsByChange() throws Exception {
    Profile profile2 = new Profile();
    profile2.setId(new ObjectId());
    profile2.setCareerLevel(Lists.newArrayList("Senior"));
    service.add(profile2);
    profile.setCareerLevel(Lists.newArrayList());
    service.add(profile);
    assertThat(service.getTags("careerLevel")).containsOnly("Senior");
  }

  @Test
  public void removeTagsByEntityDeletion() throws Exception {
    service.remove(profile);
    assertThat(service.getTags("careerLevel")).isEmpty();
    assertThat(service.getTags("degrees")).isEmpty();
  }

  @Test(expected = NullPointerException.class)
  public void removeNullEntityThrowsException() throws Exception {
    service.remove(null);
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

  @Test
  public void removeTagsByEvent() throws Exception {
    Injector injector = Guice.createInjector(new TestModule(), new Module());
    service = injector.getInstance(TagService.class);
    injector.getInstance(EventBus.class).post(new EntityChangedEvent<>(profile));
    injector.getInstance(EventBus.class).post(new EntityRemovedEvent<>(profile));

    assertThat(service.getTags("careerLevel")).isEmpty();
    assertThat(service.getTags("degrees")).isEmpty();
  }
}