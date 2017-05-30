package com.davidbyttow.catfight.framework.animation;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

public class SequenceComponent implements Component {
  Map<String, Sequence<TextureRegion>> sequences = new HashMap<>();
  SequenceState<TextureRegion> state = new SequenceState<>();

  public void addSequence(Sequence<TextureRegion> sequence) {
    sequences.put(sequence.getName(), sequence);
  }

  public void setSequence(String sequence) {
    Sequence<TextureRegion> next = sequences.get(sequence);
    Preconditions.checkState(next != null);
    state.desired = next;
  }
}
