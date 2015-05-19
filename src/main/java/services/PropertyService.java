package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.apache.commons.configuration.PropertiesConfiguration;

import dtos.GitProperties;
import ninja.utils.NinjaProperties;
import ninja.utils.SwissKnife;

@Singleton
public class PropertyService {

  private static final String GIT_PROPERTY_ABBREV = "git.commit.id.abbrev";
  private static final String GIT_PROPERTY_BRANCH = "git.branch";
  private static final String GIT_PROPERY_BUILD_TIME = "git.build.time";
  private static final String GIT_PROPERTY_COMMIT_TIME = "git.commit.time";
  private static final String GIT_PROPERTY_COMMIT_USER = "git.commit.user.name";

  @Inject
  private NinjaProperties ninjaProperties;

  private GitProperties gitProperties;

  public GitProperties getGitProperties() {
    if (gitProperties == null) {
      gitProperties = createGitProperties();
    }
    return gitProperties;
  }

  private GitProperties createGitProperties() {
    GitProperties gitProperties = new GitProperties();
    gitProperties.setShowGitProperties(renderGitProperties());
    if (gitProperties.isShowGitProperties()) {
      gitProperties.setAbbrev(getString(GIT_PROPERTY_ABBREV));
      gitProperties.setBranch(getString(GIT_PROPERTY_BRANCH));
      gitProperties.setBuildTime(getString(GIT_PROPERY_BUILD_TIME));
      gitProperties.setCommitTime(getString(GIT_PROPERTY_COMMIT_TIME));
      gitProperties.setCommitUserName(getString(GIT_PROPERTY_COMMIT_USER));
    }
    return gitProperties;
  }

  public boolean renderGitProperties() {
    return true;
    //TODO replace return true by next line, if HIRE-96 is completed.
    /*return !ninjaProperties.isProd();*/
  }

  private String getString(String key) {
    if (!renderGitProperties()) {
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
