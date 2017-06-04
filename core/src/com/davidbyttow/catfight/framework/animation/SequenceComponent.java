package com.davidbyttow.catfight.framework.animation;

import com.badlogic.ashley.core.Component;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

public abstract class SequenceComponent<T extends SequenceComponent<T>> implements Component {
  private Map<String, Sequence<T>> sequences = new HashMap<>();
  SequenceState<T> state = new SequenceState<>();

  public Sequence<T> get() {
    return state.current;
  }

  public float getAbsoluteElapsed() {
    return state.absoluteElapsed;
  }

  public float getAnimTime() {
    return state.animTime;
  }

  public boolean hasTag(String tag) {
    return state.current.getTags().contains(tag);
  }

  public void setAnimSpeed(float speed) {
    state.animSpeed = speed;
  }

  public void addSequence(Sequence<T> sequence) {
    sequences.put(sequence.getName(), sequence);
  }

  public void setSequence(String sequence) {
    Sequence<T> next = sequences.get(sequence);
    Preconditions.checkState(next != null);
    state.desired = next;
  }
}
