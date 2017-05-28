package com.davidbyttow.catfight.framework.common;

import java.util.stream.Stream;

public final class Streams {

  @FunctionalInterface
  public interface ToFloatFunction<T> {
    float applyAsFloat(T value);
  }

  public static <T> float reduceToFloat(Stream<T> stream, ToFloatFunction<T> f) {
    return stream.map(f::applyAsFloat)
        .reduce(0f, (a, b) -> a + b);
  }

  private Streams() {}
}
