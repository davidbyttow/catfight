package com.davidbyttow.catfight.framework.animation;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class SequenceSystem<T extends SequenceComponent<T>> extends IteratingSystem {

  private final Class<T> type;

  private SequenceSystem(Class<T> type, int priority) {
    super(Family.all(type).get(), priority);
    this.type = type;
  }

  public static <T extends SequenceComponent<T>> SequenceSystem<T> create(Class<T> type, int priority) {
    return new SequenceSystem<T>(type, priority);
  }

  @Override
  public final void processEntity(Entity entity, float delta) {
    T c = entity.getComponent(type);

    SequenceState<T> state = c.state;
    Sequence<T> current = state.current;

    state.absoluteElapsed += delta;
    state.animTime += delta * state.animSpeed;

    // First update the current running sequence
    if (current != null) {
      SequenceHooks<T> hooks = current.getHooks();
      hooks.update.accept(c, delta);

      Animation anim = current.getAnimation();
      KeyFrame keyFrame = anim.getKeyFrame(state.animTime);
      int frame = keyFrame.getIndex();
      if (frame > state.frame) {
        for (int i = state.frame + 1; i <= frame; ++i) {
          hooks.frame.accept(c, i);
        }
      }
      if (state.lastAnimTime < anim.getDuration() && state.animTime >= anim.getDuration()) {
        hooks.last.accept(c);
      }

      state.frame = frame;
      state.lastAnimTime = state.animTime;
    }

    // It's possible a transition occurs while the script runs.
    // TODO(d): Carry over and run the frames remaining as needed
    Sequence<T> next = state.desired;
    if (next != null && current != next) {
      if (current != null) {
        current.getHooks().exit.accept(c);
      }
      next.getHooks().enter.accept(c);

      state.desired = null;
      state.current = next;
      state.animTime = 0;
      state.lastAnimTime = 0;
      state.absoluteElapsed = 0;
      state.animSpeed = 1f;
      state.frame = -1;
    }
  }
}
