package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.apache.commons.configuration.PropertiesConfiguration;

import dtos.GitPropertyDTO;
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
  private SwissKnife swissKnife;

  @Inject
  private NinjaProperties ninjaProperties;

  private GitPropertyDTO gitPropertyDTO;

  public GitPropertyDTO getGitPropertyDTO() {
    if (gitPropertyDTO == null) {
      gitPropertyDTO = createGitPropertyDTO();
    }
    return gitPropertyDTO;
  }

  private GitPropertyDTO createGitPropertyDTO() {
    GitPropertyDTO gitPropertyDTO = new GitPropertyDTO();
    gitPropertyDTO.setShowGitProperties(renderGitProperties());
    if (gitPropertyDTO.isShowGitProperties()) {
      gitPropertyDTO.setAbbrev(getString(GIT_PROPERTY_ABBREV));
      gitPropertyDTO.setBranch(getString(GIT_PROPERTY_BRANCH));
      gitPropertyDTO.setBuildTime(getString(GIT_PROPERY_BUILD_TIME));
      gitPropertyDTO.setCommitTime(getString(GIT_PROPERTY_COMMIT_TIME));
      gitPropertyDTO.setCommitUserName(getString(GIT_PROPERTY_COMMIT_USER));
    }
    return gitPropertyDTO;
  }

  public boolean renderGitProperties() {
    return true;
    //TODO replace return true by next line, if HIRE-96 is completed.
    /*return !ninjaProperties.isProd();*/
  }

  private String getString(String key) {
    return getGitProperties() != null ? getGitProperties().getString(key) : null;
  }

  private PropertiesConfiguration getGitProperties() {
    if (ninjaProperties.isProd()) {
      return null;
    }
    return SwissKnife.loadConfigurationInUtf8("conf/git.properties");
  }
}
