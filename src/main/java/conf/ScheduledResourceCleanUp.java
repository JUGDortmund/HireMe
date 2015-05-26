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

  private static final String DELAY = "hireme.resourcecleanup.delay";
  private static final String INIT_DELAY = "hireme.resourcecleanup.initdelay";
  private static final String TIMEUNIT = "hireme.resourcecleanup.timeunit";
  private static final String EXPIRETIME = "hireme.resourcecleanup.expiretime";
  private static final int DEFAULT_EXPIRE_TIME = 1;
  private static Logger LOG = LoggerFactory.getLogger(ScheduledResourceCleanUp.class);

  private int expireTime;

  @Inject
  private NinjaProperties properties;
  @Inject
  private Datastore datastore;

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
    setExpireTime(expireTime);
  }

  public void cleanUpResources() {

    // get profiles and resources referenced by profiles
    List<Profile> profileList = datastore.find(Profile.class).filter("image !=", null).asList();
    List<ObjectId> resourcesReferencedByProfiles =
        profileList.stream().map(x -> x.getImage().getId()).collect(Collectors.toList());

    // get resources and thumbnails referenced by resources
    List<Resource> resourceList =
        datastore.find(Resource.class).filter("thumbnail !=", null).asList();
    List<ObjectId> thumbnailsReferencedByResources =
        resourceList.stream().map(x -> x.getThumbnail().getId()).collect(Collectors.toList());

    // find all old resources that are neither referenced by profiles nor by other resources
    List<Key<Resource>> oldUnreferencedResources =
        datastore.find(Resource.class).filter("id nin", resourcesReferencedByProfiles.toArray())
            .filter("id nin", thumbnailsReferencedByResources.toArray())
            .filter("lastModified <", getExpireDate()).asKeyList();

    if (oldUnreferencedResources.isEmpty()) {
      LOG.info("No unused resources found. Nothing to do...");
    } else {
      LOG.info("Found {} unused resources, deleting them from database...",
          oldUnreferencedResources.size());
      oldUnreferencedResources.forEach(x -> datastore.delete(x.getKindClass(), x.getId()));
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
    validateExpireTime(expireTime);
    this.expireTime = expireTime;
  }

  private void validateExpireTime(int expireTime) {
    if (expireTime < DEFAULT_EXPIRE_TIME) {
      throw new RuntimeException("Illegal value for expireTime, musst be atleast "
          + DEFAULT_EXPIRE_TIME);
    }
  }

}
