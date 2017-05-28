package com.davidbyttow.gdxtest;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;

public class GameScreen extends ScreenAdapter {

  private final GdxTestGame game;
  private final PooledEngine engine;
  private final World world;

  GameScreen(GdxTestGame game) {
    this.game = game;
    this.engine = new PooledEngine();

    engine.addSystem(new CameraSystem());
    engine.addSystem(new StateSystem());
    engine.addSystem(new AnimationSystem());
    engine.addSystem(new RenderingSystem(game.batcher));

    for (EntitySystem system : engine.getSystems()) {
      system.setProcessing(true);
    }

    world = World.create(engine);
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
