package com.davidbyttow.catfight.framework.script;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.davidbyttow.catfight.framework.common.SystemPriorities;

import java.util.function.Consumer;

public class ScriptSystem extends IteratingSystem {

  private static final Family FAMILY = Family.all(ScriptComponent.class).get();

  private final ComponentMapper<ScriptComponent> scriptMapper;

  public ScriptSystem() {
    super(FAMILY, SystemPriorities.TICK);
    scriptMapper = ComponentMapper.getFor(ScriptComponent.class);
  }

  @Override public void addedToEngine(Engine engine) {
    super.addedToEngine(engine);
    engine.addEntityListener(FAMILY, new EntityListener() {
      @Override public void entityAdded(Entity entity) {
        scripts(entity, s -> s.onAdded(engine, entity));
      }

      @Override public void entityRemoved(Entity entity) {
        scripts(entity, EntityScript::onRemoved);
      }
    });
    entities(e -> scripts(e, s -> s.onAdded(engine, e)));
  }

  @Override public void removedFromEngine(Engine engine) {
    entities(e -> scripts(e, EntityScript::onRemoved));
    super.removedFromEngine(engine);
  }

  @Override protected void processEntity(Entity entity, float delta) {
    scripts(entity, s -> s.update(delta));
  }

  private void entities(Consumer<Entity> consumer) {
    getEntities().forEach(consumer);
  }

  private void scripts(Entity entity, Consumer<EntityScript> consumer) {
    ScriptComponent script = scriptMapper.get(entity);
    script.entityScripts.forEach(consumer);
  }
}
