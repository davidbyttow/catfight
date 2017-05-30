package com.davidbyttow.catfight.framework.animation;

import com.badlogic.ashley.core.Entity;
import com.davidbyttow.catfight.components.AnimationComponent;

public interface SequenceHandler {
  void begin(Entity entity, AnimationComponent anim);

  void end(Entity entity, AnimationComponent anim);

  void onFrame(int frame, Entity entity, AnimationComponent anim);
}
