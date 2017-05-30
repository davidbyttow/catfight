package com.davidbyttow.catfight.framework.animation;

public interface SequenceHandler {
  default void begin() {}

  default void end() {}

  default void onFrame(int frame) {}
}
