package com.davidbyttow.catfight.framework.animation;

public class KeyFrame {

  private final int index;
  private final float duration;
  private final Object ref;

  public KeyFrame(int index, float duration, Object ref) {
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

  public Object getRef() {
    return ref;
  }
}
