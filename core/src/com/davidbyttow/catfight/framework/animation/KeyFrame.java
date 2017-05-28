package com.davidbyttow.catfight.framework.animation;

public class KeyFrame<T> {

  private final int index;
  private final float duration;
  private final T ref;

  public KeyFrame(int index, float duration, T ref) {
    this.index = index;
    this.duration = duration;
    this.ref = ref;
  }

  public int getIndex() {
    return index;
  }

  public float getDuration() {
    return duration;
  }

  public T getRef() {
    return ref;
  }
}
