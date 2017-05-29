package com.davidbyttow.catfight.game;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.davidbyttow.catfight.Catfight;
import com.davidbyttow.catfight.systems.AnimationSystem;
import com.davidbyttow.catfight.systems.CameraSystem;
import com.davidbyttow.catfight.systems.LogicSystem;
import com.davidbyttow.catfight.systems.MovementSystem;
import com.davidbyttow.catfight.systems.RenderingSystem;

public class GameScreen extends ScreenAdapter {

  private final PooledEngine engine;
  private final GameWorld gameWorld;

  public GameScreen(Catfight game) {
    this.engine = new PooledEngine();

    engine.addSystem(new CameraSystem());
    engine.addSystem(new LogicSystem());
    engine.addSystem(new MovementSystem());
    engine.addSystem(new AnimationSystem());
    engine.addSystem(new RenderingSystem(game.batcher));

    for (EntitySystem system : engine.getSystems()) {
      system.setProcessing(true);
    }

    gameWorld = GameWorld.create(engine);
  }

  private void updateScreen(float delta) {
    engine.update(delta);
  }

  private void renderScreen() {

  }

  @Override
  public void render(float delta) {
    updateScreen(delta);
    renderScreen();
  }
}
