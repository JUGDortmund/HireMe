package services;

import com.google.common.base.Preconditions;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Singleton;

import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import dtos.TagList;
import model.BaseModel;
import model.annotations.InjectLogger;
import model.annotations.Tag;
import model.events.EntityChangedEvent;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

@Singleton
public class TagService {

  private static final Map<String, Set<Field>> fieldCache = new HashMap<>();

  private final SetMultimap<String, String>
      tags =
      MultimapBuilder.hashKeys().treeSetValues().build();

  @InjectLogger
  private Logger LOG;

  /**
   * Subscribes to EntityChangedEvent and adds the Entity's values to the tag registry
   *
   * @param event Event that is send when a entity is changed
   * @param <T>   Type of the changed Entity
   */
  @Subscribe
  public <T extends BaseModel> void receiveEntityChangedEvent(@NotNull final EntityChangedEvent<T> event) {
    Preconditions.checkNotNull(event, "The event cannot be null");
    add(event.getModel());
  }

  /**
   * Adds a model's tag values to the tag registry
   *
   * @param model the given model
   * @param <T>   type of the model
   */
  public <T extends BaseModel> void add(@NotNull final T model) {
    try {
      Preconditions.checkNotNull(model, "The model cannot be null");
      tryAdd(model);
    } catch (IllegalAccessException | ClassCastException e) {
      LOG.error("Trying to add an invalid tag value", e);
    }
  }

  @SuppressWarnings("unchecked")
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

  @SuppressWarnings("unchecked")
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

  /**
   * Gets the Tags as of a given TagName as Set
   * @param tagName the given tagName
   * @return the set of tag values
   */
  public Set<String> getTags(@NotNull final String tagName) {
    Preconditions.checkNotNull(tagName, "The tagName cannot be null");

    return tags.get(tagName);
  }

  /**
   * Gets the Tags as a List of TagList dtos
   * @return List of TagList dots
   */
  public List<TagList> getTags() {
    return tags.asMap()
               .entrySet()
               .stream()
               .map(x -> new TagList(x.getKey(),
                                        new ArrayList<>(x.getValue())))
               .collect(Collectors.toList());
  }

}
