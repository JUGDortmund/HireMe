package util;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.apache.log4j.Logger;
import org.junit.Test;

import conf.Module;
import model.annotations.InjectLogger;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggerInjectorTest {

  @Test
  public void loggerUsesClassName() throws Exception {
    Injector injector = Guice.createInjector(new TestModule(), new Module());
    TestClass instance = injector.getInstance(TestClass.class);
    assertThat(instance.getLogger().getName()).isEqualTo(TestClass.class.getName());
  }

  public static class TestClass {

    @InjectLogger
    private Logger log;

    public Logger getLogger() {
      return log;
    }
  }
}
