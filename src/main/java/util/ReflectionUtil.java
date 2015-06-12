package util;

import model.BaseModel;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionUtil {

  public static boolean isStringList(@NotNull final Field field) {
    return isListOfType(field, String.class);
  }

  public static boolean isModelList(@NotNull final Field field) {
    return isListOfType(field, BaseModel.class);
  }

  private static boolean isListOfType(@NotNull final Field field, @NotNull final Class<?> clazz) {
    if (!(field.getGenericType() instanceof ParameterizedType)) {
      return false;
    }
    final Type[]
      actualTypeArguments =
      ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
    return actualTypeArguments.length == 1 &&
      field.getType().isAssignableFrom(List.class) &&
      Arrays.stream(actualTypeArguments)
        .filter(x -> clazz.isAssignableFrom((Class<?>) x))
        .collect(Collectors.counting()) == 1;
  }
}