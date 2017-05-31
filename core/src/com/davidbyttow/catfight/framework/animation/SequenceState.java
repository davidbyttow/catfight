package com.davidbyttow.catfight.framework.animation;

class SequenceState<T> {
  int frame = -1;
  float speed = 1;
  float lastTime = 0;
  float time = 0;
  Sequence<T> current;
  Sequence<T> desired;
}
