package com.davidbyttow.catfight.framework.common;

import java.util.function.Consumer;

public class Functions {
  public static <T> Consumer<T> noOpConsumer() {
    return t -> {};
  }
}
