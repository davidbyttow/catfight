package com.davidbyttow.catfight.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.davidbyttow.catfight.Assets;
import com.davidbyttow.catfight.components.ActorComponent;
import com.davidbyttow.catfight.components.AnimationComponent;
import com.davidbyttow.catfight.components.CameraComponent;
import com.davidbyttow.catfight.components.PhysicsComponent;
import com.davidbyttow.catfight.components.TextureComponent;
import com.davidbyttow.catfight.components.TransformComponent;
import com.davidbyttow.catfight.framework.input.InputComponent;
import com.davidbyttow.catfight.framework.script.ScriptComponent;
import com.davidbyttow.catfight.scripts.CatSequences;
import com.davidbyttow.catfight.scripts.PlayerScript;
import com.davidbyttow.catfight.scripts.SceneScript;
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
    CameraComponent cc = createCamera(player);
    createGround();
    createScene(cc.camera);
  }

  private Entity createPlayer() {
    Entity player = engine.createEntity();

    player.add(engine.createComponent(InputComponent.class));
    player.add(engine.createComponent(TextureComponent.class));
    player.add(engine.createComponent(TransformComponent.class));

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
    physics.body.setUserData(player);
    player.add(physics);

    ActorComponent sequence = engine.createComponent(ActorComponent.class);
    sequence.addSequence(CatSequences.IDLE);
    sequence.addSequence(CatSequences.WALK);
    sequence.addSequence(CatSequences.JUMP);
    sequence.addSequence(CatSequences.JUMP_IN_AIR);
    sequence.addSequence(CatSequences.JUMP_LAND);
    sequence.setSequence(CatSequences.IDLE.getName());
    player.add(sequence);

    AnimationComponent animation = engine.createComponent(AnimationComponent.class);
    player.add(animation);

    ScriptComponent script = engine.createComponent(ScriptComponent.class);
    script.entityScripts.add(new PlayerScript());
    player.add(script);

    engine.addEntity(player);
    return player;
  }

  private CameraComponent createCamera(Entity target) {
    Entity camera = engine.createEntity();
    CameraComponent cc = new CameraComponent();
    cc.camera = engine.getSystem(RenderingSystem.class).getCamera();
    cc.target = target;
    camera.add(cc);
    engine.addEntity(camera);
    return cc;
  }

  private void createGround() {
    for (int i = 0; i < 15; ++i) {
      Entity tile = engine.createEntity();
      TextureComponent texture = new TextureComponent();
      TransformComponent transform = new TransformComponent();
      texture.region = Assets.groundTileRegion;
      transform.pos.x = i;
      tile.add(texture);
      tile.add(transform);
      engine.addEntity(tile);
    }
    BodyDef def = new BodyDef();
    def.type = BodyDef.BodyType.StaticBody;
    def.position.set(5f, 0f);
    def.fixedRotation = true;
    def.gravityScale = 0;
    Body body = world.createBody(def);

    PolygonShape box = new PolygonShape();
    box.setAsBox(10f, 1f);
    body.createFixture(box, 0);
    box.dispose();
  }

  private void createScene(OrthographicCamera camera) {
    Entity scene = engine.createEntity();
    ScriptComponent script = engine.createComponent(ScriptComponent.class);
    TextureComponent texture = engine.createComponent(TextureComponent.class);
    TransformComponent transform = engine.createComponent(TransformComponent.class);

    script.entityScripts.add(new SceneScript(camera));

    texture.region = Assets.forestBackgroundRegion;

    scene.add(texture);
    scene.add(transform);
    scene.add(script);
    engine.addEntity(scene);
  }
}
