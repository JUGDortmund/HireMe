package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.apache.commons.configuration.PropertiesConfiguration;

import ninja.utils.NinjaProperties;
import ninja.utils.SwissKnife;

@Singleton
public class PropertyService {

  @Inject
  private SwissKnife swissKnife;

  @Inject
  private NinjaProperties ninjaProperties;

  public PropertiesConfiguration getGitProperties() {
    if (ninjaProperties.isProd()) {
      return null;
    }
    return swissKnife.loadConfigurationInUtf8("conf/git.properties");
  }

  public boolean renderGitProperties() {
    return !ninjaProperties.isProd();
  }

  public String getString(String key) {
    return getGitProperties().getString(key);
  }

  public boolean getBoolean(String key) {
    return getGitProperties().getBoolean(key);
  }
}
