package com.davidbyttow.catfight.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.google.common.base.Preconditions;

public abstract class EntityLogic implements LogicComponent.Handler {

  protected Entity entity;

  public EntityLogic(Entity entity) {
    this.entity = Preconditions.checkNotNull(entity);
  }

  @Override public void update(float delta) {}

  public <T extends Component> T getComponent(Class<T> type) {
    return Preconditions.checkNotNull(ComponentMapper.getFor(type).get(entity));
  }
}
