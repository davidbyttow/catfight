package com.davidbyttow.catfight.framework.animation;

import com.badlogic.ashley.core.Component;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

public class SequenceComponent implements Component {
  Map<String, Sequence> sequences = new HashMap<>();
  SequenceState state = new SequenceState();

  public void addSequence(Sequence sequence) {
    sequences.put(sequence.getName(), sequence);
  }

  public void setSequence(String sequence) {
    Sequence next = sequences.get(sequence);
    Preconditions.checkState(next != null);
    state.desired = next;
  }
}
