package util;

import com.google.inject.AbstractModule;

import ninja.utils.NinjaMode;
import ninja.utils.NinjaProperties;
import ninja.utils.NinjaPropertiesImpl;

public class TestModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(NinjaProperties.class).toInstance(new NinjaPropertiesImpl(NinjaMode.test));
  }
}