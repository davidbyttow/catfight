package com.davidbyttow.catfight.framework.animation;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.davidbyttow.catfight.components.AnimationComponent;
import com.davidbyttow.catfight.systems.AnimationSystem;

public class SequenceSystem extends IteratingSystem {
  public SequenceSystem() {
    super(Family.all(SequenceComponent.class, AnimationComponent.class).get(),
        AnimationSystem.PRIORITY - 1);
  }

  @Override
  public void processEntity(Entity entity, float delta) {
    SequenceComponent sc = entity.getComponent(SequenceComponent.class);
    AnimationComponent ac = entity.getComponent(AnimationComponent.class);

    SequenceState<TextureRegion> state = sc.state;
    Sequence<TextureRegion> current = sc.state.current;

    // First update the current running sequence
    if (current != null) {
      SequenceScript script = current.getScript();
      script.update(entity, delta);

      Animation<TextureRegion> anim = current.getAnim();
      KeyFrame<TextureRegion> keyFrame = anim.getKeyFrame(ac.animTime);
      int frame = keyFrame.getIndex();
      if (frame > state.frame) {
        for (int i = state.frame + 1; i <= frame; ++i) {
          script.frame(entity, i);
        }
      }

      sc.state.frame = frame;
    }

    // It's possible a transition occurs while the script runs.
    // TODO(d): Carry over and run the frames remaining as needed
    Sequence<TextureRegion> next = state.desired;
    if (next != null && current != next) {
      if (current != null) {
        current.getScript().exit(entity);
      }
      next.getScript().enter(entity);
      state.desired = null;
      state.current = next;

      Animation<TextureRegion> anim = next.getAnim();
      ac.setAnim(anim);
      sc.state.frame = -1;
    }
  }
}
