package com.davidbyttow.catfight.scripts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.davidbyttow.catfight.components.AnimationComponent;
import com.davidbyttow.catfight.components.PhysicsComponent;

public class PlayerScript extends AbstractScript {

  @Override public void update(float delta) {
    AnimationComponent animation = getComponent(AnimationComponent.class);
    PhysicsComponent physics = getComponent(PhysicsComponent.class);
    Body body = physics.body;

    Vector2 vel = body.getLinearVelocity();
    Vector2 pos = body.getPosition();

    float impulse = 0;

    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
      impulse = -0.8f;
    } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
      impulse = 0.8f;
    }

    if (Math.abs(impulse) > 0 && Math.abs(vel.x) < 5f) {
      body.applyLinearImpulse(impulse, 0, pos.x, pos.y, true);
    }

    if (Math.abs(vel.x) > 0 || impulse > 0) {
      animation.animSpeed = (Math.abs(vel.x) / 5) + 0.5f;
      animation.setAnim("walk");
    } else {
      animation.setAnim("idle");
    }
  }
}
