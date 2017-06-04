package com.davidbyttow.catfight.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.davidbyttow.catfight.framework.animation.SequenceComponent;

import java.util.function.Consumer;

public class ActorComponent extends SequenceComponent<ActorComponent> {
  private Entity entity;

  public boolean inAir;

  public Entity getEntity() {
    return entity;
  }

  public static ActorComponent create(Engine engine, Entity entity) {
    ActorComponent ac = new ActorComponent();
    ac.entity = entity;
    return ac;
  }

  public void checkCollision(Consumer<Entity> consumer) {

  }

  public <T extends Component> T getComponent(Class<T> type) {
    return entity.getComponent(type);
  }
}
