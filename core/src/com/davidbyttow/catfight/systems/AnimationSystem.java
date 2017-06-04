package com.davidbyttow.catfight.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.davidbyttow.catfight.components.ActorComponent;
import com.davidbyttow.catfight.components.TextureComponent;
import com.davidbyttow.catfight.framework.animation.Animation;
import com.davidbyttow.catfight.framework.animation.KeyFrame;
import com.davidbyttow.catfight.framework.animation.Sequence;

public class AnimationSystem extends IteratingSystem {

  public AnimationSystem(int priority) {
    super(Family.all(TextureComponent.class, ActorComponent.class).get(), priority);
 }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    TextureComponent texture = entity.getComponent(TextureComponent.class);
    ActorComponent actor = entity.getComponent(ActorComponent.class);

    Sequence<?> seq = actor.get();

    if (seq != null) {
      Animation anim = seq.getAnimation();
      KeyFrame kf = anim.getKeyFrame(actor.getAnimTime());
      Object ref = kf.getRef();
      if (ref instanceof TextureRegion) {
        texture.region = (TextureRegion) ref;
      }
    }
  }
}
