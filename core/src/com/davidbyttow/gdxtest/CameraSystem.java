package com.davidbyttow.gdxtest;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class CameraSystem extends IteratingSystem {

  private ComponentMapper<TransformComponent> transformMapepr;
  private ComponentMapper<CameraComponent> cameraMapper;

  public CameraSystem() {
    super(Family.all(CameraComponent.class).get());

    transformMapepr = ComponentMapper.getFor(TransformComponent.class);
    cameraMapper = ComponentMapper.getFor(CameraComponent.class);
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    CameraComponent cam = cameraMapper.get(entity);

    if (cam.target == null) {
      return;
    }

    TransformComponent target = transformMapepr.get(cam.target);

    if (target == null) {
      return;
    }

    cam.camera.position.y = Math.max(cam.camera.position.y, target.pos.y);
  }
}
