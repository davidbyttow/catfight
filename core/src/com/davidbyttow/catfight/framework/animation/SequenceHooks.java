package com.davidbyttow.catfight.framework.animation;

import com.badlogic.ashley.core.Entity;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class SequenceHooks {
  Consumer<Entity> enter = t -> {};
  Consumer<Entity> exit = t -> {};
  Consumer<Entity> last = t -> {};
  BiConsumer<Entity, Float> update = (t, u) -> {};
  BiConsumer<Entity, Integer> frame = (t, u) -> {};
  Function<Entity, Boolean> loop = t -> false;
}
