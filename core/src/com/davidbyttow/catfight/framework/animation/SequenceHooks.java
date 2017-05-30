package com.davidbyttow.catfight.framework.animation;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class SequenceHooks<T> {
  Consumer<T> enter = t -> {};
  Consumer<T> exit = t -> {};
  Consumer<T> last = t -> {};
  BiConsumer<T, Float> update = (t, u) -> {};
  BiConsumer<T, Integer> frame = (t, u) -> {};
  Function<T, Boolean> loop = t -> false;
}
