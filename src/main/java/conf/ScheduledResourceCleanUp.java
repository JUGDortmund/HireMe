package conf;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import model.Profile;
import model.Resource;

import ninja.scheduler.Schedule;
import ninja.utils.NinjaProperties;

@Singleton
public class ScheduledResourceCleanUp {

  @Inject
  private NinjaProperties properties;
  @Inject
  private Datastore datastore;
  private static final String DELAY = "hireme.resourcecleanup.delay";
  private static final String INIT_DELAY = "hireme.resourcecleanup.initdelay";
  private static final String TIMEUNIT = "hireme.resourcecleanup.timeunit";
  private static final String EXPIRETIME = "hireme.resourcecleanup.expiretime";
  private int expireTime;
  public static final int DEFAULT_EXPIRE_TIME = 1;
  Logger LOG = LoggerFactory.getLogger(ScheduledResourceCleanUp.class);


  @Schedule(delayProperty = DELAY, initialDelayProperty = INIT_DELAY, timeUnitProperty = TIMEUNIT)
  public void doCleanUp() {
    LOG.info("-------------------------------------------------------------------");
    LOG.info("Started resource cleanup");
    LOG.info("-------------------------------------------------------------------");
    cleanUpResources();
    LOG.info("-------------------------------------------------------------------");
    LOG.info("Finished resource cleanup");
    LOG.info("-------------------------------------------------------------------");
  }

  @Inject
  public ScheduledResourceCleanUp(NinjaProperties properties, Datastore datastore) {
    this.properties = properties;
    this.datastore = datastore;
    expireTime = properties.getIntegerWithDefault(EXPIRETIME, DEFAULT_EXPIRE_TIME);
    if (expireTime < DEFAULT_EXPIRE_TIME) {
      throw new RuntimeException("Illegal value for expireTime, musst be atleast "
          + DEFAULT_EXPIRE_TIME);
    }
  }

  public void cleanUpResources() {

    List<Profile> profileList = datastore.find(Profile.class).filter("image !=", null).asList();
    List<ObjectId> resourceIds =
        profileList.stream().map(x -> x.getImage().getId()).collect(Collectors.toList());
    List<Key<Resource>> resourceKeys =
        datastore.find(Resource.class).filter("id nin", resourceIds.toArray())
            .filter("lastModified <", getExpireDate()).asKeyList();
    if (resourceKeys.isEmpty()) {
      LOG.info("No unused resources found. Nothing to do...");
    } else {
      LOG.info("Found {} unused resources, deleting them from database...", resourceKeys.size());
      resourceKeys.forEach(x -> datastore.delete(x.getKindClass(), x.getId()));
    }
  }

  private Date getExpireDate() {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1 * expireTime);
    return cal.getTime();
  }

  public int getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(int expireTime) {
    this.expireTime = expireTime;
  }


}
