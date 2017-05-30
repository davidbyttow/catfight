package com.davidbyttow.catfight.systems;

import com.badlogic.ashley.core.Entity;
import com.davidbyttow.catfight.components.ActorComponent;
import com.davidbyttow.catfight.framework.animation.SequenceSystem;

public class ActorSystem extends SequenceSystem<Entity> {
  public ActorSystem() {
    super(ActorComponent.class);
  }

  @Override protected Entity adapt(Entity entity) {
    return entity;
  }
}
