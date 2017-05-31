package com.davidbyttow.catfight.framework.animation;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

public class SequenceComponent implements Component {
  private Map<String, Sequence<Entity>> sequences = new HashMap<>();
  SequenceState<Entity> state = new SequenceState<>();

  public void addSequence(Sequence<Entity> sequence) {
    sequences.put(sequence.getName(), sequence);
  }

  public void setSequence(String sequence) {
    Sequence<Entity> next = sequences.get(sequence);
    Preconditions.checkState(next != null);
    state.desired = next;
  }
}
