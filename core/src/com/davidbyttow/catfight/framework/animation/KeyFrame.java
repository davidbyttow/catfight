package com.davidbyttow.catfight.framework.animation;

public class KeyFrame<T> {

  private final float duration;
  private final T ref;

  KeyFrame(float duration, T ref) {
    this.duration = duration;
    this.ref = ref;
  }

  public float getDuration() {
    return duration;
  }

  public T getRef() {
    return ref;
  }
}
