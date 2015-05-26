package integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import conf.ScheduledResourceCleanUp;

import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import java.util.Calendar;
import java.util.Date;

import model.Profile;
import model.Resource;

import ninja.NinjaTest;

public class ScheduledResourceCleanUpTest extends NinjaTest {

  private Datastore datastore;
  private ScheduledResourceCleanUp cleaner;
  private Resource resource;
  private Calendar cal;

  @Before
  public void setup() {
    datastore = getInjector().getInstance(Datastore.class);
    cleaner = getInjector().getInstance(ScheduledResourceCleanUp.class);
    resource = new Resource();
    cal = Calendar.getInstance();
    cleaner.setExpireTime(1);
  }

  @Test
  public void unusedOldResourcesGettingCleaned() {
    setOldCalendarDate();
    final Date date = cal.getTime();

    resource.setLastModified(date);
    datastore.save(resource);
    assertTrue(datastore.find(Resource.class).asList().contains(resource));

    cleaner.cleanUpResources();
    assertFalse(datastore.find(Resource.class).asList().contains(resource));
  }

  @Test
  public void newResourcesGettingNotCleaned() {
    final Date date = cal.getTime();

    resource.setLastModified(date);
    datastore.save(resource);
    assertTrue(datastore.find(Resource.class).asList().contains(resource));

    cleaner.cleanUpResources();
    assertTrue(datastore.find(Resource.class).asList().contains(resource));
  }

  @Test
  public void usedOldResourcesGettingNotCleaned() {
    final Profile profile = new Profile();
    setOldCalendarDate();
    final Date date = cal.getTime();

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
  public void usedOldThumbnailsGettingNotCleaned() {
    final Resource thumbnail = new Resource();
    setOldCalendarDate();
    final Date date = cal.getTime();

    thumbnail.setLastModified(date);
    datastore.save(thumbnail);
    assertTrue(datastore.find(Resource.class).asList().contains(thumbnail));

    resource.setThumbnail(thumbnail);
    datastore.save(resource);
    assertTrue(datastore.find(Resource.class).asList().contains(resource));

    cleaner.cleanUpResources();
    assertTrue(datastore.find(Resource.class).asList().contains(thumbnail));
  }

  private void setOldCalendarDate() {
    cal.add(Calendar.DATE, -2);
  }
}
