package com.davidbyttow.catfight.framework.animation;

import com.badlogic.ashley.core.Entity;
import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class Sequence {

  private final String name;
  private final Animation animation;
  private Set<String> tags = new HashSet<>();
  private SequenceHooks hooks = new SequenceHooks();

  private Sequence(String name, Animation animation) {
    this.name = name;
    this.animation = animation;
  }

  public String getName() {
    return name;
  }

  public Animation getAnimation() {
    return animation;
  }

  SequenceHooks getHooks() {
    return hooks;
  }

  public static Builder builder(String name, Animation animation) {
    return new Builder(new Sequence(name, animation));
  }

  public static class Builder {
    private Sequence sequence;

    private Builder(Sequence sequence) {
      this.sequence = sequence;
    }

    public Sequence build() {
      return this.sequence;
    }

    public Builder enter(Consumer<Entity> hook) {
      sequence.hooks.enter = hook;
      return this;
    }

    public Builder exit(Consumer<Entity> hook) {
      sequence.hooks.exit = hook;
      return this;
    }

    public Builder last(Consumer<Entity> hook) {
      sequence.hooks.last = hook;
      return this;
    }

    public Builder update(BiConsumer<Entity, Float> hook) {
      sequence.hooks.update = hook;
      return this;
    }

    public Builder frame(BiConsumer<Entity, Integer> hook) {
      sequence.hooks.frame = hook;
      return this;
    }

    public Builder loop(boolean b) {
      sequence.hooks.loop = (e) -> b;
      return this;
    }

    public Builder loop(Function<Entity, Boolean> loop) {
      sequence.hooks.loop = loop;
      return this;
    }

    public Builder tags(String ...tags) {
      this.sequence.tags.addAll(ImmutableSet.copyOf(tags));
      return this;
    }
  }
}
