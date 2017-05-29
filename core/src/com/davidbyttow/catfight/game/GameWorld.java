package com.davidbyttow.catfight.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.davidbyttow.catfight.Assets;
import com.davidbyttow.catfight.components.AnimationComponent;
import com.davidbyttow.catfight.components.CameraComponent;
import com.davidbyttow.catfight.components.PhysicsComponent;
import com.davidbyttow.catfight.components.ScriptComponent;
import com.davidbyttow.catfight.components.TextureComponent;
import com.davidbyttow.catfight.components.TransformComponent;
import com.davidbyttow.catfight.scripts.PlayerScript;
import com.davidbyttow.catfight.systems.RenderingSystem;

public class GameWorld {
  private final PooledEngine engine;
  private final World world;

  private GameWorld(PooledEngine engine, World world) {
    this.engine = engine;
    this.world = world;
  }

  public static GameWorld create(PooledEngine engine, World world) {
    GameWorld gameWorld = new GameWorld(engine, world);
    gameWorld.init();
    return gameWorld;
  }

  private void init() {
    Entity player = createPlayer();
    createCamera(player);
    createGround();
  }

  private Entity createPlayer() {
    Entity player = engine.createEntity();
    AnimationComponent animation = engine.createComponent(AnimationComponent.class);
    ScriptComponent script = engine.createComponent(ScriptComponent.class);
    TextureComponent texture = engine.createComponent(TextureComponent.class);
    TransformComponent transform = engine.createComponent(TransformComponent.class);
    PhysicsComponent physics = engine.createComponent(PhysicsComponent.class);

    BodyDef def = new BodyDef();
    def.type = BodyDef.BodyType.DynamicBody;
    def.position.set(5f, 5f);
    Body body = world.createBody(def);
    body.setFixedRotation(true);

    CircleShape circle = new CircleShape();
    circle.setRadius(0.5f);
    circle.setPosition(new Vector2(0, 0.5f));
    Fixture fixture = body.createFixture(circle, 1);
    fixture.setFriction(10);
    circle.dispose();
    physics.body = body;

    script.scripts.add(new PlayerScript());

    animation.animations.put("idle", Assets.catIdle);
    animation.animations.put("walk", Assets.catWalk);
    animation.animName = "idle";

    player.add(script);
    player.add(animation);
    player.add(texture);
    player.add(transform);
    player.add(physics);
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

  private void createGround() {
    BodyDef def = new BodyDef();
    def.type = BodyDef.BodyType.StaticBody;
    def.position.set(5f, 0f);
    def.fixedRotation = true;
    def.gravityScale = 0;
    Body body = world.createBody(def);

    PolygonShape box = new PolygonShape();
    box.setAsBox(10f, 0.5f);
    body.createFixture(box, 0);
    box.dispose();
  }
}