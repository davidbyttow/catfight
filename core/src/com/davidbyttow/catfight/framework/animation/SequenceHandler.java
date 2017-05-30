package com.davidbyttow.catfight.framework.animation;

public interface SequenceHandler {
  default void enter() {}

  default void exit() {}

  default void last() {}

  default void frame(int frame) {}

  default void update(float delta) {}

  default boolean loop() { return false; }
}
