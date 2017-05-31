package com.davidbyttow.catfight.framework.animation;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.davidbyttow.catfight.components.AnimationComponent;
import com.davidbyttow.catfight.systems.AnimationSystem;

public class SequenceSystem extends IteratingSystem {

  // TODO(d): Decouple animation system from here
  public SequenceSystem() {
    super(Family.all(SequenceComponent.class, AnimationComponent.class).get(),
        AnimationSystem.PRIORITY - 1);
  }

  @Override
  public final void processEntity(Entity entity, float delta) {
    SequenceComponent component = entity.getComponent(SequenceComponent.class);
    AnimationComponent ac = entity.getComponent(AnimationComponent.class);

    SequenceState<Entity> state = component.state;
    Sequence<Entity> current = component.state.current;

    state.elapsed += delta;

    // First update the current running sequence
    if (current != null) {
      SequenceHooks<Entity> hooks = current.getHooks();
      hooks.update.accept(entity, delta);

      Animation anim = current.getAnimation();
      KeyFrame keyFrame = anim.getKeyFrame(ac.animTime);
      int frame = keyFrame.getIndex();
      if (frame > state.frame) {
        for (int i = state.frame + 1; i <= frame; ++i) {
          hooks.frame.accept(entity, i);
        }
      }
      if (ac.lastAnimTime < anim.getDuration() && ac.animTime >= anim.getDuration()) {
        hooks.last.accept(entity);
      }

      component.state.frame = frame;
    }

    // It's possible a transition occurs while the script runs.
    // TODO(d): Carry over and run the frames remaining as needed
    Sequence<Entity> next = state.desired;
    if (next != null && current != next) {
      if (current != null) {
        current.getHooks().exit.accept(entity);
      }
      next.getHooks().enter.accept(entity);
      state.desired = null;
      state.current = next;
      state.elapsed = 0;

      Animation anim = next.getAnimation();
      ac.setAnim(anim);
      component.state.frame = -1;
    }
  }
}
