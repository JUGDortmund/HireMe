package conf;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import org.apache.log4j.Logger;

import java.util.Arrays;

import model.annotations.InjectLogger;

public class LoggerTypeListener implements TypeListener {

  @Override
  public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
    Class<?> clazz = type.getRawType();
    while (clazz != null) {
      Arrays.stream(clazz.getDeclaredFields())
          .filter(x -> x.getType() == Logger.class && x.isAnnotationPresent(InjectLogger.class))
          .forEach(x -> encounter.register(new LoggerMembersInjector<>(x)));
      clazz = clazz.getSuperclass();
    }
  }
}
