package com.davidbyttow.catfight.framework.animation;

import com.badlogic.ashley.core.Entity;

public interface SequenceScript {
  default void enter(Entity entity) {}

  default void exit(Entity entity) {}

  default void last(Entity entity) {}

  default void frame(Entity entity, int frame) {}

  default void update(Entity entity, float delta) {}

  default boolean loop(Entity entity) { return false; }
}
