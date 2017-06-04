package com.davidbyttow.catfight.framework.animation;

class SequenceState<T extends SequenceComponent<T>> {
  int frame = -1;
  float animSpeed = 1;
  float lastAnimTime = 0;
  float animTime = 0;
  float absoluteElapsed = 0;
  Sequence<T> current;
  Sequence<T> desired;
}
