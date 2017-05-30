package com.davidbyttow.catfight.framework.animation;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.davidbyttow.catfight.components.AnimationComponent;
import com.davidbyttow.catfight.systems.AnimationSystem;

public abstract class SequenceSystem<T> extends IteratingSystem {

  private final Class<? extends SequenceComponent<T>> type;

  public SequenceSystem(Class<? extends SequenceComponent<T>> type) {
    super(Family.all(type, AnimationComponent.class).get(),
        AnimationSystem.PRIORITY - 1);
    this.type = type;
  }

  protected abstract T adapt(Entity entity);

  @Override
  public final void processEntity(Entity entity, float delta) {
    SequenceComponent<T> sc = entity.getComponent(type);
    AnimationComponent ac = entity.getComponent(AnimationComponent.class);

    SequenceState<T> state = sc.state;
    Sequence<T> current = sc.state.current;

    T actor = adapt(entity);

    // First update the current running sequence
    if (current != null) {
      SequenceHooks<T> hooks = current.getHooks();
      hooks.update.accept(actor, delta);

      Animation anim = current.getAnimation();
      KeyFrame keyFrame = anim.getKeyFrame(ac.animTime);
      int frame = keyFrame.getIndex();
      int lastFrame = anim.getLastKeyFrame().getIndex();
      if (frame > state.frame) {
        for (int i = state.frame + 1; i <= frame; ++i) {
          hooks.frame.accept(actor, i);
          if (i == lastFrame) {
            hooks.last.accept(actor);
          }
        }
      }

      sc.state.frame = frame;
    }

    // It's possible a transition occurs while the script runs.
    // TODO(d): Carry over and run the frames remaining as needed
    Sequence<T> next = state.desired;
    if (next != null && current != next) {
      if (current != null) {
        current.getHooks().exit.accept(actor);
      }
      next.getHooks().enter.accept(actor);
      state.desired = null;
      state.current = next;

      Animation anim = next.getAnimation();
      ac.setAnim(anim);
      sc.state.frame = -1;
    }
  }
}
