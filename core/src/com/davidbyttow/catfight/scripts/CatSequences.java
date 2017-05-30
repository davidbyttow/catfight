package com.davidbyttow.catfight.scripts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.davidbyttow.catfight.Assets;
import com.davidbyttow.catfight.framework.animation.Sequence;
import com.davidbyttow.catfight.framework.animation.SequenceHandler;

public final class CatSequences {
  public static final Sequence<TextureRegion> IDLE = Sequence.fromAnim("idle", Assets.catIdle, new SequenceHandler() {
    @Override public boolean loop() {
      return true;
    }
  });

  public static final Sequence<TextureRegion> WALK = Sequence.fromAnim("walk", Assets.catWalk, new SequenceHandler() {
    @Override public boolean loop() {
      return true;
    }
  });

  private CatSequences() {}
}
