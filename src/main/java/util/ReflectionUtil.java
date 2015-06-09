package util;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionUtil {

  public static boolean isStringList(@NotNull final Field field) {
    if (!(field.getGenericType() instanceof ParameterizedType)) {
      return false;
    }
    final Type[]
      actualTypeArguments =
      ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
    return actualTypeArguments.length == 1 &&
      field.getType().isAssignableFrom(List.class) &&
      Arrays.stream(actualTypeArguments)
        .filter(x -> ((Class<?>) x).isAssignableFrom(String.class))
        .collect(Collectors.counting()) == 1;
  }
}