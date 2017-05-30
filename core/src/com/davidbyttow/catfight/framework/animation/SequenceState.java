package com.davidbyttow.catfight.framework.animation;

class SequenceState<T> {
  int frame = -1;
  Sequence<T> current;
  Sequence<T> desired;
}
