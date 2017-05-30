package com.davidbyttow.catfight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.davidbyttow.catfight.components.AnimationComponent;
import com.davidbyttow.catfight.components.SequenceComponent;
import com.davidbyttow.catfight.framework.animation.Sequence;

public class SequenceSystem extends IteratingSystem {
  private ComponentMapper<SequenceComponent> sequenceMapper;
  private ComponentMapper<AnimationComponent> animationMapper;

  public SequenceSystem() {
    super(Family.all(SequenceComponent.class, AnimationComponent.class).get(),
        AnimationSystem.PRIORITY - 1);

    sequenceMapper = ComponentMapper.getFor(SequenceComponent.class);
    animationMapper = ComponentMapper.getFor(AnimationComponent.class);
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    SequenceComponent tc = sequenceMapper.get(entity);
    AnimationComponent ac = animationMapper.get(entity);

    Sequence<TextureRegion> sequence = tc.sequences.get(tc.sequence);
    ac.setAnim(sequence.getAnim());
  }
}
