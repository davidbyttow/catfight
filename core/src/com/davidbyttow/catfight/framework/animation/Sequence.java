package com.davidbyttow.catfight.framework.animation;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class Sequence<T> {

  private final String name;
  private final Animation animation;
  private Set<String> tags = new HashSet<>();
  private SequenceHooks<T> hooks = new SequenceHooks<>();

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

  SequenceHooks<T> getHooks() {
    return hooks;
  }

  public static <T> Builder<T> builder(String name, Animation animation) {
    return new Builder<>(new Sequence<>(name, animation));
  }

  public static class Builder<T> {
    private Sequence<T> sequence;

    private Builder(Sequence<T> sequence) {
      this.sequence = sequence;
    }

    public Sequence<T> build() {
      return this.sequence;
    }

    public Builder<T> enter(Consumer<T> hook) {
      sequence.hooks.enter = hook;
      return this;
    }

    public Builder<T> exit(Consumer<T> hook) {
      sequence.hooks.exit = hook;
      return this;
    }

    public Builder<T> last(Consumer<T> hook) {
      sequence.hooks.last = hook;
      return this;
    }

    public Builder<T> update(BiConsumer<T, Float> hook) {
      sequence.hooks.update = hook;
      return this;
    }

    public Builder<T> frame(BiConsumer<T, Integer> hook) {
      sequence.hooks.frame = hook;
      return this;
    }

    public Builder<T> loop(boolean b) {
      sequence.hooks.loop = (e) -> b;
      return this;
    }

    public Builder<T> loop(Function<T, Boolean> loop) {
      sequence.hooks.loop = loop;
      return this;
    }

    public Builder<T> tags(String ...tags) {
      this.sequence.tags.addAll(ImmutableSet.copyOf(tags));
      return this;
    }
  }
}
