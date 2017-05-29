package com.davidbyttow.catfight.scripts;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.google.common.base.Preconditions;

public abstract class AbstractEntityScript implements EntityScript {

  private Engine engine;
  protected Entity entity;

  @Override public final void onAdded(Engine engine, Entity entity) {
    this.engine = engine;
    this.entity = entity;
    load();
  }

  @Override public final void onRemoved() {
    unload();
    this.engine = null;
    this.entity = null;
  }

  protected void load() {}

  protected void unload() {}

  @Override public void update(float delta) {}

  @Override public <T extends Component> T getComponent(Class<T> type) {
    return Preconditions.checkNotNull(ComponentMapper.getFor(type).get(entity));
  }

  public Engine getEngine() {
    return engine;
  }

  public Entity getEntity() {
    return entity;
  }
}
