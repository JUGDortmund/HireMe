package services;

import com.google.common.base.Preconditions;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Singleton;
import dtos.TagList;
import model.BaseModel;
import model.annotations.InjectLogger;
import model.annotations.Tag;
import model.events.EntityChangedEvent;
import model.events.EntityRemovedEvent;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import util.ReflectionUtil;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

@Singleton
public class TagService {

  private static final Map<String, Set<Field>> fieldCache = new HashMap<>();

  private final ListMultimap<String, String>
      tags = MultimapBuilder.hashKeys().arrayListValues().build();
  private final ListMultimap<ObjectId, TagAssociation<?>>
      tagAssociations =
      MultimapBuilder.ListMultimapBuilder.hashKeys().arrayListValues().build();

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
    tagAssociations.get(model.getId()).forEach(x -> tags.remove(x.key, x.value));
    tagAssociations.removeAll(model);
    for (final Field field : getFields(model.getClass())) {
      field.setAccessible(true);
      final Object value = field.get(model);
      if (value == null) {
        continue;
      }
      if (ReflectionUtil.isStringList(field)) {
        final Collection<String> values = (Collection<String>) value;
        values.forEach(x -> tagAssociations.put(model.getId(), new TagAssociation<>(field.getName(),
                                                                                       x)));
        tags.putAll(field.getName(), values);
      } else if (field.getType().isAssignableFrom(String.class)) {
        tagAssociations.put(model.getId(), new TagAssociation<>(field.getName(), (String) value));
        tags.put(field.getName(), (String) value);
      }
    }
  }

  @Subscribe
  public <T extends BaseModel> void receiveEntityRemovedEvent(@NotNull final EntityRemovedEvent<T> event) {
    Preconditions.checkNotNull(event, "The event cannot be null");
    remove(event.getModel());
  }

  public <T extends BaseModel> void remove(@NotNull T model) {
    tagAssociations.get(model.getId()).forEach(x -> tags.remove(x.key, x.value));
    tagAssociations.removeAll(model);
  }

  @SuppressWarnings("unchecked")
  private Set<Field> getFields(final Class<? extends BaseModel> modelClass) {
    if (!fieldCache.containsKey(modelClass.getName())) {
      fieldCache.put(modelClass.getName(), getAllFields(modelClass, withAnnotation(Tag.class)));
    }
    return fieldCache.get(modelClass.getName());
  }

  /**
   * Gets the Tags as of a given TagName as Set
   *
   * @param tagName the given tagName
   * @return the set of tag values
   */
  public Set<String> getTags(@NotNull final String tagName) {
    Preconditions.checkNotNull(tagName, "The tagName cannot be null");

    return new TreeSet<>(tags.get(tagName));
  }

  /**
   * Gets the Tags as a List of TagList dtos
   *
   * @return List of TagList dots
   */
  public List<TagList> getTags() {
    return tags.asMap()
               .entrySet()
               .stream()
               .map(x -> new TagList(x.getKey(), new ArrayList<>(new TreeSet<>(x.getValue()))))
               .collect(Collectors.toList());
  }

  private class TagAssociation<T extends BaseModel> {

    String key;
    String value;

    private TagAssociation(String key, String value) {
      this.key = key;
      this.value = value;
    }
  }

}
