package com.davidbyttow.catfight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.davidbyttow.catfight.components.PhysicsComponent;
import com.davidbyttow.catfight.components.TransformComponent;

public class PhysicsSystem extends IteratingSystem {

  private final World world;
  private final ComponentMapper<PhysicsComponent> physicsMapper;
  private final ComponentMapper<TransformComponent> transformMapper;

  public PhysicsSystem(World world) {
    super(Family.all(PhysicsComponent.class, TransformComponent.class).get());
    this.world = world;
    physicsMapper = ComponentMapper.getFor(PhysicsComponent.class);
    transformMapper = ComponentMapper.getFor(TransformComponent.class);
  }

  @Override protected void processEntity(Entity entity, float delta) {
    PhysicsComponent physics = physicsMapper.get(entity);
    TransformComponent transform = transformMapper.get(entity);
    Body body = physics.body;

    Vector2 pos = body.getPosition();
    transform.pos.set(pos.x, pos.y, 0);
    if (!body.isFixedRotation()) {
      transform.rotation = body.getAngle();
    }
  }
}
