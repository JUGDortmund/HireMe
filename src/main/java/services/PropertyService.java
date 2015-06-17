package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dtos.BuildProperties;
import ninja.utils.NinjaProperties;
import ninja.utils.SwissKnife;
import org.apache.commons.configuration.PropertiesConfiguration;

@Singleton
public class PropertyService {

  public static final String SHOW_BUILD_INFO = "hireMe.showBuildInfo";
  public static final String ENVIRONMENT = "hireMe.env.name";
  private static final String GIT_PROPERTY_ABBREV = "git.commit.id.abbrev";
  private static final String GIT_PROPERTY_BRANCH = "git.branch";
  private static final String GIT_PROPERY_BUILD_TIME = "git.build.time";
  private static final String GIT_PROPERTY_COMMIT_TIME = "git.commit.time";
  private static final String GIT_PROPERTY_COMMIT_USER = "git.commit.user.name";

  @Inject
  private NinjaProperties ninjaProperties;

  private BuildProperties buildProperties;

  public BuildProperties getBuildProperties() {
    if (buildProperties == null) {
      buildProperties = createBuildProperties();
    }
    return buildProperties;
  }

  private BuildProperties createBuildProperties() {
    BuildProperties buildProperties = new BuildProperties();
    buildProperties.setShowBuildProperties(renderBuildProperties());
    if (buildProperties.isShowBuildProperties()) {
      buildProperties.setAbbrev(getString(GIT_PROPERTY_ABBREV));
      buildProperties.setBranch(getString(GIT_PROPERTY_BRANCH));
      buildProperties.setBuildTime(getString(GIT_PROPERY_BUILD_TIME));
      buildProperties.setCommitTime(getString(GIT_PROPERTY_COMMIT_TIME));
      buildProperties.setCommitUserName(getString(GIT_PROPERTY_COMMIT_USER));
      buildProperties.setEnvironment(ninjaProperties.get(ENVIRONMENT));
    }
    return buildProperties;
  }

  public boolean renderBuildProperties() {
    return ninjaProperties.getBooleanWithDefault(SHOW_BUILD_INFO, true);
  }

  private String getString(String key) {
    if (!renderBuildProperties()) {
      return null;
    }
    return getPropertiesFile().getString(key);
  }

  private PropertiesConfiguration getPropertiesFile() {
    return SwissKnife.loadConfigurationInUtf8("conf/git.properties");
  }

  public boolean showMinifiedVersion() {
    return !(ninjaProperties.isTest() || ninjaProperties.isDev());
  }
}
