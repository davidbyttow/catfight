package com.davidbyttow.catfight.scripts;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.davidbyttow.catfight.components.TextureComponent;
import com.davidbyttow.catfight.components.TransformComponent;
import com.davidbyttow.catfight.framework.common.Units;
import com.davidbyttow.catfight.framework.script.AbstractEntityScript;

public class SceneScript extends AbstractEntityScript {

  private final OrthographicCamera camera;

  public SceneScript(OrthographicCamera camera) {
    this.camera = camera;
  }

  @Override public void update(float delta) {
    TextureComponent texture = getComponent(TextureComponent.class);
    TransformComponent transform = getComponent(TransformComponent.class);
    transform.pos.x = camera.position.x;
    transform.pos.y = camera.position.y - camera.viewportHeight * 0.5f;
    transform.pos.z = 1000f;
    float scale = 1f;
    if (texture.region.getRegionWidth() < texture.region.getRegionHeight()) {
      scale = camera.viewportWidth * Units.METERS_TO_PIXELS / texture.region.getRegionWidth();
    } else {
      scale = camera.viewportHeight * Units.METERS_TO_PIXELS / texture.region.getRegionHeight();
    }
    transform.scale.x = scale;
    transform.scale.y = scale;
  }
}
