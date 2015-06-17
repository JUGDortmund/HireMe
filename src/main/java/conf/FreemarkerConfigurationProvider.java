package conf;

import com.google.inject.Provider;
import com.google.inject.Singleton;

import freemarker.template.Configuration;
import services.ProfileExportService;

import ninja.template.TemplateEngineFreemarkerEscapedLoader;

/**
 * @author Lukas Eichler
 */
@Singleton
public class FreemarkerConfigurationProvider implements Provider<Configuration> {

  @Override
  public Configuration get() {
    final Configuration cfg = new Configuration(Configuration.getVersion());
    cfg.setClassForTemplateLoading(getClass(), "/" + ProfileExportService.TEMPLATES_PATH + "/");

    // we are going to enable html escaping by default using this template loader:
    cfg.setTemplateLoader(new TemplateEngineFreemarkerEscapedLoader(cfg.getTemplateLoader()));
    return cfg;
  }
}
