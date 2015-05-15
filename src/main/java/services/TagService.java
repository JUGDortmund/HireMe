package services;

import com.google.common.base.Preconditions;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Singleton;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import model.BaseModel;
import model.Tag;
import model.events.EntityChangedEvent;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

@Singleton
public class TagService {

  private static final Map<String, Set<Field>> fieldCache = new HashMap<>();

  private final SetMultimap<String, String>
      tags =
      MultimapBuilder.hashKeys().treeSetValues().build();

  @Subscribe
  public <T extends BaseModel> void receiveEntityChangedEvent(@NotNull final EntityChangedEvent<T> event) {
    Preconditions.checkNotNull(event, "The event cannot be null");
    add(event.getModel());
  }

  public <T extends BaseModel> void add(@NotNull final T model) {
    try {
      Preconditions.checkNotNull(model, "The model cannot be null");
      tryAdd(model);
    } catch (IllegalAccessException | ClassCastException e) {
      e.printStackTrace();
    }
  }

  private <T extends BaseModel> void tryAdd(@NotNull final T model) throws IllegalAccessException {
    for (final Field field : getFields(model.getClass())) {
      field.setAccessible(true);
      final Object value = field.get(model);
      if (value == null) {
        continue;
      }
      if (isStringList(field)) {
        tags.putAll(field.getName(), (Collection<String>) value);
      } else if (field.getType().isAssignableFrom(String.class)) {
        tags.put(field.getName(), (String) value);
      }
    }
  }

  private Set<Field> getFields(final Class<? extends BaseModel> modelClass) {
    if (!fieldCache.containsKey(modelClass.getName())) {
      fieldCache.put(modelClass.getName(), getAllFields(modelClass, withAnnotation(Tag.class)));
    }
    return fieldCache.get(modelClass.getName());
  }

  private boolean isStringList(@NotNull final Field field) {
    final Type[]
        actualTypeArguments =
        ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
    return actualTypeArguments.length == 1 &&
           field.getType().isAssignableFrom(List.class) &&
           Arrays.stream(actualTypeArguments)
               .filter(x -> ((Class<?>) x).isAssignableFrom(String.class))
               .collect(Collectors.counting()) == 1;
  }

  public Set<String> getTags(@NotNull final String tagName) {
    Preconditions.checkNotNull(tagName, "The tagName cannot be null");

    return tags.get(tagName);
  }

  public Map<String, Collection<String>> getTags() {
    return tags.asMap();
  }
}
