package com.davidbyttow.catfight.framework.animation;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class SequenceSystem extends IteratingSystem {

  public SequenceSystem(int priority) {
    super(Family.all(SequenceComponent.class).get(), priority);
  }

  @Override
  public final void processEntity(Entity entity, float delta) {
    SequenceComponent c = entity.getComponent(SequenceComponent.class);

    SequenceState<Entity> state = c.state;
    Sequence<Entity> current = state.current;

    state.time += delta * state.speed;

    // First update the current running sequence
    if (current != null) {
      SequenceHooks<Entity> hooks = current.getHooks();
      hooks.update.accept(entity, delta);

      Animation anim = current.getAnimation();
      KeyFrame keyFrame = anim.getKeyFrame(state.time);
      int frame = keyFrame.getIndex();
      if (frame > state.frame) {
        for (int i = state.frame + 1; i <= frame; ++i) {
          hooks.frame.accept(entity, i);
        }
      }
      if (state.lastTime < anim.getDuration() && state.time >= anim.getDuration()) {
        hooks.last.accept(entity);
      }

      state.frame = frame;
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
      state.time = 0;
      state.lastTime = 0;
      state.speed = 1f;
      state.frame = -1;
    }
  }
}
