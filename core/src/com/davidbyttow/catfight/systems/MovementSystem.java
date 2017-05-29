package com.davidbyttow.catfight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.davidbyttow.catfight.components.MovementComponent;
import com.davidbyttow.catfight.components.TransformComponent;
import com.davidbyttow.catfight.framework.common.Vectors;

public class MovementSystem extends IteratingSystem {
  private ComponentMapper<TransformComponent> transformMapper;
  private ComponentMapper<MovementComponent> movementMapper;

  public MovementSystem() {
    super(Family.all(TransformComponent.class, MovementComponent.class).get());

    transformMapper = ComponentMapper.getFor(TransformComponent.class);
    movementMapper = ComponentMapper.getFor(MovementComponent.class);
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    TransformComponent transform = transformMapper.get(entity);
    MovementComponent movement = movementMapper.get(entity);

    movement.vel.add(Vectors.scale(movement.accel, deltaTime));
    transform.pos.add(movement.vel.x * deltaTime, movement.vel.y * deltaTime, 0);
  }
}
