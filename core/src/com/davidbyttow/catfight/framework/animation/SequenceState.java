package com.davidbyttow.catfight.framework.animation;

class SequenceState<T> {
  int frame = -1;
  float elapsed = 0;
  Sequence<T> current;
  Sequence<T> desired;
}
