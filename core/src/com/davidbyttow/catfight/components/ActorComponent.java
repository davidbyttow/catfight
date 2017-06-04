package com.davidbyttow.catfight.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.davidbyttow.catfight.framework.animation.SequenceComponent;

public class ActorComponent extends SequenceComponent<ActorComponent> {
  public boolean inAir;
  public Entity entity;

  public <T extends Component> T getComponent(Class<T> type) {
    return entity.getComponent(type);
  }
}
