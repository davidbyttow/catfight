package com.davidbyttow.catfight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.davidbyttow.catfight.components.CameraComponent;
import com.davidbyttow.catfight.components.TransformComponent;
import com.davidbyttow.catfight.framework.common.SystemPriorities;

public class CameraSystem extends IteratingSystem {

  private ComponentMapper<TransformComponent> transformMapper;
  private ComponentMapper<CameraComponent> cameraMapper;

  public CameraSystem() {
    super(Family.all(CameraComponent.class).get(), SystemPriorities.PRE_RENDER);

    transformMapper = ComponentMapper.getFor(TransformComponent.class);
    cameraMapper = ComponentMapper.getFor(CameraComponent.class);
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    CameraComponent cam = cameraMapper.get(entity);

    if (cam.target == null) {
      return;
    }

    TransformComponent target = transformMapper.get(cam.target);

    if (target == null) {
      return;
    }

//    cam.camera.position.x = target.pos.x;
  }
}
