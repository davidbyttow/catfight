package com.davidbyttow.catfight.framework.script;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import java.util.function.Consumer;

public abstract class ScriptableSystem extends IteratingSystem {

  private final ComponentMapper<ScriptComponent> scriptMapper;

  public ScriptableSystem(Family family, int priority) {
    super(family, priority);
    this.scriptMapper = ComponentMapper.getFor(ScriptComponent.class);
  }

  @Override protected final void processEntity(Entity entity, float deltaTime) {
    update(entity, deltaTime);
    ScriptComponent script = scriptMapper.get(entity);
    if (script != null) {
      script.entityScripts.forEach(s -> handleScript(s, deltaTime));
    }
  }

  protected void update(Entity entity, float deltaTime) {}

  protected abstract void handleScript(EntityScript script, float deltaTime);

  public void forEachScript(Consumer<EntityScript> s) {
    this.getEntities().forEach(entity -> {
      ScriptComponent script = scriptMapper.get(entity);
      if (script != null) {
        script.entityScripts.forEach(s);
      }
    });
  }
}
