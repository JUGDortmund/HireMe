package conf;

import com.google.inject.MembersInjector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;


public class LoggerMembersInjector<T> implements MembersInjector<T> {

  private final Field field;
  private final Logger logger;

  public LoggerMembersInjector(Field field) {
    this.field = field;
    this.logger = LoggerFactory.getLogger(field.getDeclaringClass());
    field.setAccessible(true);
  }

  @Override
  public void injectMembers(T instance) {
    try {
      field.set(instance, logger);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
