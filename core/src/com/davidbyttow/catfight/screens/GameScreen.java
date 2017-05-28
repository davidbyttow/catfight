package com.davidbyttow.catfight.screens;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.davidbyttow.catfight.CatfightGame;
import com.davidbyttow.catfight.GameWorld;
import com.davidbyttow.catfight.systems.AnimationSystem;
import com.davidbyttow.catfight.systems.CameraSystem;
import com.davidbyttow.catfight.systems.RenderingSystem;
import com.davidbyttow.catfight.systems.StateSystem;

public class GameScreen extends ScreenAdapter {

  private final PooledEngine engine;
  private final GameWorld gameWorld;

  public GameScreen(CatfightGame game) {
    this.engine = new PooledEngine();

    engine.addSystem(new CameraSystem());
    engine.addSystem(new StateSystem());
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
