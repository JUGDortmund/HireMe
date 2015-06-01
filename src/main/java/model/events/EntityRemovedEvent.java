package model.events;

import com.google.common.base.Preconditions;

import javax.validation.constraints.NotNull;

import model.BaseModel;

/**
 * Event to indicate the removal of an entity
 */
public class EntityRemovedEvent<T extends BaseModel> {

  private final T model;

  public EntityRemovedEvent(final @NotNull T model) {
    Preconditions.checkNotNull(model);
    this.model = model;
  }

  public T getModel() {
    return model;
  }
}
