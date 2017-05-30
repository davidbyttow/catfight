package com.davidbyttow.catfight.framework.animation;

public class Sequence<T> {
  private final String name;
  private final Animation<T> anim;
  private final SequenceHandler handler;

  private Sequence(String name, Animation<T> anim, SequenceHandler handler) {
    this.name = name;
    this.anim = anim;
    this.handler = handler;
  }

  public static <T> Sequence<T> fromAnim(String name, Animation<T> anim, SequenceHandler handler) {
    return new Sequence<>(name, anim, handler);
  }

  public Animation<T> getAnim() {
    return anim;
  }

  public String getName() {
    return name;
  }

  public SequenceHandler getHandler() {
    return handler;
  }
}
