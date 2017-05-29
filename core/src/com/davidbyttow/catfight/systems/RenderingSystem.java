package com.davidbyttow.catfight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.davidbyttow.catfight.components.TextureComponent;
import com.davidbyttow.catfight.components.TransformComponent;

import java.util.Comparator;

import static com.badlogic.ashley.core.Family.all;

public class RenderingSystem extends IteratingSystem {
  static final float FRUSTUM_WIDTH = 10;
  static final float PIXELS_TO_METERS = 1.0f / 32.0f;

  private SpriteBatch batch;
  private Array<Entity> renderQueue;
  private Comparator<Entity> comparator;
  private OrthographicCamera cam;

  private ComponentMapper<TextureComponent> textureMapper;
  private ComponentMapper<TransformComponent> transformMapper;

  public RenderingSystem(SpriteBatch batch) {
    super(all(TransformComponent.class, TextureComponent.class).get());

    textureMapper = ComponentMapper.getFor(TextureComponent.class);
    transformMapper = ComponentMapper.getFor(TransformComponent.class);

    renderQueue = new Array<>();

    comparator = (entityA, entityB) -> (int)Math.signum(transformMapper.get(entityB).pos.z - transformMapper.get(entityA).pos.z);

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

      if (tex.region == null) {
        continue;
      }

      TransformComponent t = transformMapper.get(entity);

      float width = tex.region.getRegionWidth();
      float height = tex.region.getRegionHeight();
      float originX = width * 0.5f;
      float originY = 0;
      float x = t.pos.x - originX;
      float y = t.pos.y;
      float sx = t.scale.x * PIXELS_TO_METERS;
      float sy = t.scale.y * PIXELS_TO_METERS;

      batch.draw(tex.region,
          x, y,
          originX, originY,
          width, height,
          sx, sy,
          MathUtils.radiansToDegrees * t.rotation);
    }

    batch.end();
    renderQueue.clear();
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    renderQueue.add(entity);
  }

  public OrthographicCamera getCamera() {
    return cam;
  }
}
