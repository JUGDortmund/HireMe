package conf;

import com.google.inject.MembersInjector;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;

public class LoggerMembersInjector<T> implements MembersInjector<T> {

  private final Field field;
  private final Logger logger;

  public LoggerMembersInjector(Field field) {
    this.field = field;
    this.logger = Logger.getLogger(field.getDeclaringClass());
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
