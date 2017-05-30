package com.davidbyttow.catfight.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.davidbyttow.catfight.framework.animation.Sequence;

import java.util.HashMap;
import java.util.Map;

public class SequenceComponent implements Component {
  public Map<String, Sequence<TextureRegion>> sequences = new HashMap<>();
  private String sequence;
  public int lastFrame = -1;

  public String getSequence() {
    return sequence;
  }

  public void setSequence(String sequence) {
    if (!sequence.equals(this.sequence)) {
      this.sequence = sequence;
      lastFrame = -1;
    }
  }
}
