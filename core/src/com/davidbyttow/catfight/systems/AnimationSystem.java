package com.davidbyttow.catfight.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.davidbyttow.catfight.components.TextureComponent;
import com.davidbyttow.catfight.framework.animation.KeyFrame;
import com.davidbyttow.catfight.framework.animation.Sequence;
import com.davidbyttow.catfight.framework.animation.SequenceComponent;

public class AnimationSystem extends IteratingSystem {

  public AnimationSystem(int priority) {
    super(Family.all(TextureComponent.class, SequenceComponent.class).get(), priority);
 }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    TextureComponent tc = entity.getComponent(TextureComponent.class);
    SequenceComponent sc = entity.getComponent(SequenceComponent.class);

    Sequence<?> seq = sc.get();

    if (seq != null) {
      seq.getAnimation()
      KeyFrame kf = ac.anim.getKeyFrame(ac.animTime);
      Object ref = kf.getRef();
      if (ref instanceof TextureRegion) {
        tc.region = (TextureRegion) ref;
      }
    }

    ac.lastAnimTime = ac.animTime;
    ac.animTime += ac.animSpeed * deltaTime;
  }
}
