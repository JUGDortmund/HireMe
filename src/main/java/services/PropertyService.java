package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.apache.commons.configuration.PropertiesConfiguration;

import dtos.GitPropertyDTO;
import ninja.utils.NinjaProperties;
import ninja.utils.SwissKnife;

@Singleton
public class PropertyService {

  @Inject
  private SwissKnife swissKnife;

  @Inject
  private NinjaProperties ninjaProperties;

  private PropertiesConfiguration getGitProperties() {
    if (ninjaProperties.isProd()) {
      return null;
    }
    return swissKnife.loadConfigurationInUtf8("conf/git.properties");
  }

  public boolean renderGitProperties() {
    return !ninjaProperties.isProd();
  }

  private String getString(String key) {
    return getGitProperties().getString(key);
  }

  private boolean getBoolean(String key) {
    return getGitProperties().getBoolean(key);
  }

  public GitPropertyDTO getGitPropertyDTO() {
    GitPropertyDTO gitPropertyDTO = new GitPropertyDTO();
    gitPropertyDTO.setAbbrev(getString("git.commit.id.abbrev"));
    gitPropertyDTO.setBranch(getString("git.branch"));
    gitPropertyDTO.setBuildTime(getString("git.build.time"));
    gitPropertyDTO.setCommitTime(getString("git.commit.time"));
    gitPropertyDTO.setCommitUserName(getString("git.commit.user.name"));
    gitPropertyDTO.setShowGitProperties(renderGitProperties());
    return gitPropertyDTO;
  }
}
