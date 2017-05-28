package com.davidbyttow.catfight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.davidbyttow.catfight.components.AnimationComponent;
import com.davidbyttow.catfight.components.StateComponent;
import com.davidbyttow.catfight.components.TextureComponent;
import com.davidbyttow.catfight.framework.animation.Animation;
import com.davidbyttow.catfight.framework.animation.KeyFrame;

public class AnimationSystem extends IteratingSystem {
  private ComponentMapper<TextureComponent> textureMapper;
  private ComponentMapper<AnimationComponent> animationMapper;
  private ComponentMapper<StateComponent> stateMapper;

  public AnimationSystem() {
    super(Family.all(TextureComponent.class,
        AnimationComponent.class,
        StateComponent.class).get());

    textureMapper = ComponentMapper.getFor(TextureComponent.class);
    animationMapper = ComponentMapper.getFor(AnimationComponent.class);
    stateMapper = ComponentMapper.getFor(StateComponent.class);
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    TextureComponent tex = textureMapper.get(entity);
    AnimationComponent anim = animationMapper.get(entity);
    StateComponent state = stateMapper.get(entity);

    Animation<TextureRegion> animation = anim.animations.get(state.get());

    if (animation != null) {
      KeyFrame<TextureRegion> kf = animation.getKeyFrame(state.time);
      tex.region = kf.getRef();
    }

    state.time += deltaTime;
  }
}
