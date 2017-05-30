package com.davidbyttow.catfight.framework.animation;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
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

    SequenceState state = sc.state;
    Sequence current = sc.state.current;

    // First update the current running sequence
    if (current != null) {
      SequenceHooks hooks = current.getHooks();
      hooks.update.accept(entity, delta);

      Animation anim = current.getAnimation();
      KeyFrame keyFrame = anim.getKeyFrame(ac.animTime);
      int frame = keyFrame.getIndex();
      int lastFrame = anim.getLastKeyFrame().getIndex();
      if (frame > state.frame) {
        for (int i = state.frame + 1; i <= frame; ++i) {
          hooks.frame.accept(entity, i);
          if (i == lastFrame) {
            hooks.last.accept(entity);
          }
        }
      }

      sc.state.frame = frame;
    }

    // It's possible a transition occurs while the script runs.
    // TODO(d): Carry over and run the frames remaining as needed
    Sequence next = state.desired;
    if (next != null && current != next) {
      if (current != null) {
        current.getHooks().exit.accept(entity);
      }
      next.getHooks().enter.accept(entity);
      state.desired = null;
      state.current = next;

      Animation anim = next.getAnimation();
      ac.setAnim(anim);
      sc.state.frame = -1;
    }
  }
}
