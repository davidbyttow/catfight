package com.davidbyttow.catfight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.davidbyttow.catfight.components.TextureComponent;
import com.davidbyttow.catfight.components.TransformComponent;
import com.davidbyttow.catfight.framework.common.Units;

import java.util.Comparator;

import static com.badlogic.ashley.core.Family.all;

public class RenderingSystem extends IteratingSystem {
  static final float FRUSTUM_WIDTH = 10;

  private final SpriteBatch batch;
  private final Array<Entity> renderQueue = new Array<>();
  private final Comparator<Entity> comparator;
  private final OrthographicCamera cam;
  private final World world;
  private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

  private ComponentMapper<TextureComponent> textureMapper;
  private ComponentMapper<TransformComponent> transformMapper;

  public RenderingSystem(SpriteBatch batch, World world) {
    super(all(TransformComponent.class, TextureComponent.class).get());

    textureMapper = ComponentMapper.getFor(TextureComponent.class);
    transformMapper = ComponentMapper.getFor(TransformComponent.class);

    this.world = world;
    this.comparator = (entityA, entityB) -> (int)Math.signum(transformMapper.get(entityB).pos.z - transformMapper.get(entityA).pos.z);
    this.batch = batch;

    float height = FRUSTUM_WIDTH * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
    cam = new OrthographicCamera(FRUSTUM_WIDTH, height);
    cam.position.set(FRUSTUM_WIDTH * 0.5f, height * 0.5f, 0);
  }

  @Override
  public void update(float deltaTime) {
    super.update(deltaTime);

    renderQueue.sort(comparator);

    cam.update();
    batch.setProjectionMatrix(cam.combined);
    batch.begin();

    for (Entity entity : renderQueue) {
      TextureComponent tex = textureMapper.get(entity);
      TextureRegion region = tex.region;

      if (region == null) {
        continue;
      }

      TransformComponent t = transformMapper.get(entity);

      if (region.isFlipX() ^ t.facingLeft) {
        region.flip(true, false);
      }

        float width = region.getRegionWidth();
      float height = region.getRegionHeight();
      float originX = width * 0.5f;
      float originY = 0;
      float x = t.pos.x - originX;
      float y = t.pos.y;
      float sx = t.scale.x * Units.PIXELS_TO_METERS;
      float sy = t.scale.y * Units.PIXELS_TO_METERS;

      batch.draw(region,
          x, y,
          originX, originY,
          width, height,
          sx, sy,
          MathUtils.radiansToDegrees * t.rotation);
    }

    batch.end();
    renderQueue.clear();

//    debugRenderer.render(world, cam.combined);
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    renderQueue.add(entity);
  }

  public OrthographicCamera getCamera() {
    return cam;
  }
}
