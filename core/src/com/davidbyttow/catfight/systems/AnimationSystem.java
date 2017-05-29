package com.davidbyttow.catfight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.davidbyttow.catfight.components.AnimationComponent;
import com.davidbyttow.catfight.components.TextureComponent;
import com.davidbyttow.catfight.framework.animation.Animation;
import com.davidbyttow.catfight.framework.animation.KeyFrame;

public class AnimationSystem extends IteratingSystem {
  private ComponentMapper<TextureComponent> textureMapper;
  private ComponentMapper<AnimationComponent> animationMapper;

  public AnimationSystem() {
    super(Family.all(TextureComponent.class,
        AnimationComponent.class).get());

    textureMapper = ComponentMapper.getFor(TextureComponent.class);
    animationMapper = ComponentMapper.getFor(AnimationComponent.class);
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    TextureComponent tc = textureMapper.get(entity);
    AnimationComponent ac = animationMapper.get(entity);

    Animation<TextureRegion> anim = ac.animations.get(ac.animName);
    if (anim != null) {
      KeyFrame<TextureRegion> kf = anim.getKeyFrame(ac.animTime);
      tc.region = kf.getRef();
    }

    ac.animTime += deltaTime;
  }
}
