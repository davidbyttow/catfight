package com.davidbyttow.catfight;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.davidbyttow.catfight.components.AnimationComponent;
import com.davidbyttow.catfight.components.CameraComponent;
import com.davidbyttow.catfight.components.TextureComponent;
import com.davidbyttow.catfight.components.TransformComponent;
import com.davidbyttow.catfight.systems.RenderingSystem;

public class GameWorld {
  private final PooledEngine engine;

  private GameWorld(PooledEngine engine) {
    this.engine = engine;
  }

  public static GameWorld create(PooledEngine engine) {
    GameWorld gameWorld = new GameWorld(engine);
    gameWorld.init();
    return gameWorld;
  }

  private void init() {
    Entity player = createPlayer();
    createCamera(player);
  }

  private Entity createPlayer() {
    Entity player = engine.createEntity();
    AnimationComponent animation = engine.createComponent(AnimationComponent.class);
    TextureComponent texture = engine.createComponent(TextureComponent.class);
    TransformComponent transform = engine.createComponent(TransformComponent.class);

    animation.animations.put("idle", Assets.catIdle);
    animation.animations.put("walk", Assets.catWalk);
    animation.animName = "idle";

    transform.pos.set(0.f, 0.f, 0.f);
    player.add(animation);
    player.add(texture);
    player.add(transform);
    engine.addEntity(player);
    return player;
  }

  private void createCamera(Entity target) {
    Entity camera = engine.createEntity();
    CameraComponent cc = new CameraComponent();
    cc.camera = engine.getSystem(RenderingSystem.class).getCamera();
    cc.target = target;
    camera.add(cc);
    engine.addEntity(camera);
  }
}
