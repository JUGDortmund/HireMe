package model.events;

import model.BaseModel;

/**
 * Event to indicate a changed Entity
 */
public class EntityChangedEvent<T extends BaseModel> {

  private T model;

  public EntityChangedEvent(T model) {
    this.model = model;
  }

  public T getModel() {
    return model;
  }

  public void setModel(T model) {
    this.model = model;
  }
}
