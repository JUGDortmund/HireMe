package conf;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import freemarker.template.Configuration;
import services.ProfileExportService;

/**
 * @author Lukas Eichler
 */
@Singleton
public class ConfigurationProvider implements Provider<Configuration> {

  @Override
  public Configuration get() {
    final Configuration cfg = new Configuration(Configuration.getVersion());
    cfg.setClassForTemplateLoading(getClass(), "/" + ProfileExportService.TEMPLATES_PATH + "/");
    return cfg;
  }
}
