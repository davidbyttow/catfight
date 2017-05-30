package com.davidbyttow.catfight.framework.animation;

public class Sequence<T> {
  private final String name;
  private final Animation<T> anim;
  private final SequenceScript script;

  private Sequence(String name, Animation<T> anim, SequenceScript script) {
    this.name = name;
    this.anim = anim;
    this.script = script;
  }

  public static <T> Sequence<T> create(String name, Animation<T> anim, SequenceScript script) {
    return new Sequence<>(name, anim, script);
  }

  public Animation<T> getAnim() {
    return anim;
  }

  public String getName() {
    return name;
  }

  public SequenceScript getScript() {
    return script;
  }
}
