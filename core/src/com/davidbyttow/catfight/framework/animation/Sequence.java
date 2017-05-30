package com.davidbyttow.catfight.framework.animation;

public class Sequence<T> {
  private final String name;
  private final Animation<T> anim;

  private Sequence(String name, Animation<T> anim) {
    this.name = name;
    this.anim = anim;
  }

  public static <T> Sequence<T> fromAnim(String name, Animation<T> anim) {
    return new Sequence<>(name, anim);
  }

  public Animation<T> getAnim() {
    return anim;
  }

  public String getName() {
    return name;
  }
}
