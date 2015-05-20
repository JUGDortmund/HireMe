package integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;

import model.Profile;
import model.Resource;
import ninja.NinjaTest;

import org.junit.Test;
import org.mongodb.morphia.Datastore;

import conf.ScheduledResourceCleanUp;

public class ScheduledResourceCleanUpTest extends NinjaTest {

  @Test
  public void testThatUnusedOldResourcesGettingCleaned() {

    final Datastore datastore = getInjector().getInstance(Datastore.class);
    final Resource resource = new Resource();
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -2);
    final Date date = cal.getTime();
    final ScheduledResourceCleanUp cleaner =
        getInjector().getInstance(ScheduledResourceCleanUp.class);
    cleaner.setExpireTime(1);

    resource.setLastModified(date);
    datastore.save(resource);
    cleaner.cleanUpResources();

    assertThat(datastore.find(Resource.class).asKeyList()).hasSize(0);
  }

  @Test
  public void testThatUnusedNewResourcesGettingNotCleaned() {

    final Datastore datastore = getInjector().getInstance(Datastore.class);
    final Resource resource = new Resource();
    Calendar cal = Calendar.getInstance();
    final Date date = cal.getTime();
    final ScheduledResourceCleanUp cleaner =
        getInjector().getInstance(ScheduledResourceCleanUp.class);

    resource.setLastModified(date);
    datastore.save(resource);
    cleaner.cleanUpResources();

    assertThat(datastore.find(Resource.class).asKeyList()).hasSize(1);
  }

  @Test
  public void testThatUsedOldResourcesGettingNotCleaned() {

    final Datastore datastore = getInjector().getInstance(Datastore.class);
    final Resource resource = new Resource();
    final Profile profile = new Profile();
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -2);
    final Date date = cal.getTime();
    final ScheduledResourceCleanUp cleaner =
        getInjector().getInstance(ScheduledResourceCleanUp.class);
    cleaner.setExpireTime(1);

    resource.setLastModified(date);
    datastore.save(resource);
    cleaner.cleanUpResources();

    resource.setLastModified(date);
    profile.setImage(resource);
    datastore.save(resource);
    datastore.save(profile);

    cleaner.cleanUpResources();

    assertThat(datastore.find(Resource.class).asKeyList()).hasSize(1);
  }

  @Test
  public void testThatUsedNewResourcesGettingNotCleaned() {

    final Datastore datastore = getInjector().getInstance(Datastore.class);
    final Resource resource = new Resource();
    final Profile profile = new Profile();
    Calendar cal = Calendar.getInstance();
    final Date date = cal.getTime();
    final ScheduledResourceCleanUp cleaner =
        getInjector().getInstance(ScheduledResourceCleanUp.class);

    resource.setLastModified(date);
    profile.setImage(resource);
    datastore.save(resource);
    datastore.save(profile);

    cleaner.cleanUpResources();

    assertThat(datastore.find(Resource.class).asKeyList()).hasSize(1);
  }
}
