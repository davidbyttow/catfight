package com.davidbyttow.catfight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.davidbyttow.catfight.components.AnimationComponent;
import com.davidbyttow.catfight.components.SequenceComponent;
import com.davidbyttow.catfight.framework.animation.Animation;
import com.davidbyttow.catfight.framework.animation.KeyFrame;
import com.davidbyttow.catfight.framework.animation.Sequence;
import com.davidbyttow.catfight.framework.animation.SequenceHandler;
import com.davidbyttow.catfight.framework.script.EntityScript;
import com.davidbyttow.catfight.framework.script.ScriptableSystem;

public class SequenceSystem extends ScriptableSystem {
  private ComponentMapper<SequenceComponent> sequenceMapper;
  private ComponentMapper<AnimationComponent> animationMapper;

  public SequenceSystem() {
    super(Family.all(SequenceComponent.class, AnimationComponent.class).get(),
        AnimationSystem.PRIORITY - 1);

    sequenceMapper = ComponentMapper.getFor(SequenceComponent.class);
    animationMapper = ComponentMapper.getFor(AnimationComponent.class);
  }

  @Override
  public void update(Entity entity, float deltaTime) {
    SequenceComponent tc = sequenceMapper.get(entity);
    AnimationComponent ac = animationMapper.get(entity);

    Sequence<TextureRegion> sequence = tc.sequences.get(tc.getSequence());
    Animation<TextureRegion> anim = sequence.getAnim();
    ac.setAnim(anim);

    KeyFrame<TextureRegion> keyFrame = anim.getKeyFrame(ac.animTime);
    int frame = keyFrame.getIndex();
    if (frame > tc.lastFrame) {
      if (frame == 0) {
        forEachScript(SequenceHandler::onEnterSequence);
      }
      for (int i = tc.lastFrame + 1; i <= frame; ++i) {
        final int thisFrame = i;
        forEachScript(s -> s.onSequenceFrame(thisFrame));
      }
    }
    tc.lastFrame = frame;
  }

  @Override protected void handleScript(EntityScript script, float deltaTime) {
  }
}
