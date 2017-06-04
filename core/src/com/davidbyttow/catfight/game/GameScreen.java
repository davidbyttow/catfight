package com.davidbyttow.catfight.game;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.davidbyttow.catfight.Catfight;
import com.davidbyttow.catfight.components.ActorComponent;
import com.davidbyttow.catfight.framework.animation.SequenceSystem;
import com.davidbyttow.catfight.framework.common.SystemPriorities;
import com.davidbyttow.catfight.framework.input.CompositeInputProcessor;
import com.davidbyttow.catfight.framework.input.InputSystem;
import com.davidbyttow.catfight.framework.script.ScriptSystem;
import com.davidbyttow.catfight.systems.AnimationSystem;
import com.davidbyttow.catfight.systems.CameraSystem;
import com.davidbyttow.catfight.systems.PhysicsSystem;
import com.davidbyttow.catfight.systems.RenderingSystem;

public class GameScreen extends ScreenAdapter {

  private final float TIME_STEP = 1 / 45.f;

  private final PooledEngine engine;
  private final World world;
  private final GameWorld gameWorld;
  private float accumulatedTime = 0;

  public GameScreen(Catfight game) {
    this.engine = new PooledEngine();
    this.world = new World(new Vector2(0, -9.8f), true);

    CompositeInputProcessor inputProcessor = new CompositeInputProcessor();
    Gdx.input.setInputProcessor(inputProcessor);

    engine.addSystem(new PhysicsSystem(world));
    engine.addSystem(new CameraSystem());
    engine.addSystem(new ScriptSystem());
    engine.addSystem(SequenceSystem.create(ActorComponent.class, SystemPriorities.POST_TICK));
    engine.addSystem(new AnimationSystem(SystemPriorities.PRE_RENDER));
    engine.addSystem(new RenderingSystem(game.batcher, world));
    engine.addSystem(new InputSystem(inputProcessor));

    for (EntitySystem system : engine.getSystems()) {
      system.setProcessing(true);
    }

    gameWorld = GameWorld.create(engine, world);
  }

  private void updateScreen(float delta) {
    accumulatedTime += delta;
    while (accumulatedTime >= TIME_STEP) {
      accumulatedTime -= TIME_STEP;
      world.step(TIME_STEP, 6, 2);
    }
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
