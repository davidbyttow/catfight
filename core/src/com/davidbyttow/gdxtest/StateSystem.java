package com.davidbyttow.gdxtest;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class StateSystem extends IteratingSystem {
  private final ComponentMapper<StateComponent> mapper;

  public StateSystem() {
    super(Family.all(StateComponent.class).get());

    mapper = ComponentMapper.getFor(StateComponent.class);
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    mapper.get(entity).time += deltaTime;
  }
}
