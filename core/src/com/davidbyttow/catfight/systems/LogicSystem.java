package com.davidbyttow.catfight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.davidbyttow.catfight.components.LogicComponent;

public class LogicSystem extends IteratingSystem {
  private final ComponentMapper<LogicComponent> logicMapper;

  public LogicSystem() {
    super(Family.all(LogicComponent.class).get());
    logicMapper = ComponentMapper.getFor(LogicComponent.class);
  }

  @Override protected void processEntity(Entity entity, float delta) {
    LogicComponent logic = logicMapper.get(entity);
    logic.handlers.forEach(h -> h.update(delta));
  }
}
