package integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import conf.ScheduledResourceCleanUp;

import org.junit.Test;
import org.mongodb.morphia.Datastore;

import java.util.Calendar;
import java.util.Date;

import model.Profile;
import model.Resource;

import ninja.NinjaTest;

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
    assertTrue(datastore.find(Resource.class).asList().contains(resource));

    cleaner.cleanUpResources();
    assertFalse(datastore.find(Resource.class).asList().contains(resource));
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
    assertTrue(datastore.find(Resource.class).asList().contains(resource));

    cleaner.cleanUpResources();
    assertTrue(datastore.find(Resource.class).asList().contains(resource));
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
    assertTrue(datastore.find(Resource.class).asList().contains(resource));

    profile.setImage(resource);
    datastore.save(profile);
    assertTrue(datastore.find(Profile.class).asList().contains(profile));

    cleaner.cleanUpResources();
    assertTrue(datastore.find(Resource.class).asList().contains(resource));
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
    datastore.save(resource);
    assertTrue(datastore.find(Resource.class).asList().contains(resource));

    profile.setImage(resource);
    datastore.save(profile);
    assertTrue(datastore.find(Profile.class).asList().contains(profile));

    cleaner.cleanUpResources();
    assertTrue(datastore.find(Resource.class).asList().contains(resource));
  }


  @Test
  public void testThatUsedOldThumbnailsGettingNotCleaned() {

    final Datastore datastore = getInjector().getInstance(Datastore.class);
    final Resource resource = new Resource();
    final Resource thumbnail = new Resource();
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -2);
    final Date date = cal.getTime();
    final ScheduledResourceCleanUp cleaner =
        getInjector().getInstance(ScheduledResourceCleanUp.class);
    cleaner.setExpireTime(1);

    thumbnail.setLastModified(date);
    datastore.save(thumbnail);
    assertTrue(datastore.find(Resource.class).asList().contains(thumbnail));

    resource.setThumbnail(thumbnail);
    datastore.save(resource);
    assertTrue(datastore.find(Resource.class).asList().contains(resource));

    cleaner.cleanUpResources();
    assertTrue(datastore.find(Resource.class).asList().contains(thumbnail));
  }

  @Test
  public void testThatUsedNewThumbnailsGettingNotCleaned() {

    final Datastore datastore = getInjector().getInstance(Datastore.class);
    final Resource resource = new Resource();
    final Resource thumbnail = new Resource();
    Calendar cal = Calendar.getInstance();
    final Date date = cal.getTime();
    final ScheduledResourceCleanUp cleaner =
        getInjector().getInstance(ScheduledResourceCleanUp.class);

    thumbnail.setLastModified(date);
    datastore.save(thumbnail);
    assertTrue(datastore.find(Resource.class).asList().contains(thumbnail));

    resource.setThumbnail(thumbnail);
    datastore.save(resource);
    assertTrue(datastore.find(Resource.class).asList().contains(resource));

    cleaner.cleanUpResources();
    assertTrue(datastore.find(Resource.class).asList().contains(thumbnail));
  }
}
