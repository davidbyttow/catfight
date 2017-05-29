package com.davidbyttow.catfight.scripts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.davidbyttow.catfight.components.AnimationComponent;
import com.davidbyttow.catfight.components.MovementComponent;

public class PlayerScript extends AbstractScript {

  @Override public void update(float delta) {
    AnimationComponent animation = getComponent(AnimationComponent.class);
    MovementComponent movement = getComponent(MovementComponent.class);
    movement.accel.x = 0;

    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
      movement.accel.x = -15f;
    } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
      movement.accel.x = 15f;
    } else {
      movement.vel.x = 0;
    }
    if (movement.vel.x > 5f) {
      movement.vel.x = 5f;
    } else if (movement.vel.x < -5f) {
      movement.vel.x = -5f;
    }

    System.out.println(movement.vel.x);

    if (Math.abs(movement.vel.x) > 0) {
      animation.animSpeed = (Math.abs(movement.vel.x) / 5) + 0.5f;
      animation.setAnim("walk");
    } else {
      animation.setAnim("idle");
    }
  }
}
