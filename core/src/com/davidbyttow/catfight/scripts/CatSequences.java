package com.davidbyttow.catfight.scripts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.davidbyttow.catfight.Assets;
import com.davidbyttow.catfight.components.ActorComponent;
import com.davidbyttow.catfight.components.PhysicsComponent;
import com.davidbyttow.catfight.components.TransformComponent;
import com.davidbyttow.catfight.framework.animation.Sequence;

public interface CatSequences {

  static void updateNav(ActorComponent actor) {
    TransformComponent transform = actor.getComponent(TransformComponent.class);
    Body body = actor.getComponent(PhysicsComponent.class).body;

    boolean waiting = actor.hasTag(SequenceTags.IDLE) || actor.hasTag(SequenceTags.WALKING);

    if (waiting) {
      if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
        actor.setSequence(JUMP.getName());
        waiting = false;
      } else if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
        actor.setSequence(ATTACK_JAB.getName());
        waiting = false;
      } else if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
        actor.setSequence(ATTACK_STRAIGHT.getName());
        waiting = false;
      }
    }

    Vector2 vel = body.getLinearVelocity();
    Vector2 pos = body.getPosition();

    float impulse = 0;

    if (waiting) {
      if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
        impulse = -0.8f;
        transform.facingLeft = true;
      } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
        impulse = 0.8f;
        transform.facingLeft = false;
      }
    }

    if (Math.abs(impulse) > 0 && Math.abs(vel.x) < 5f) {
      body.applyLinearImpulse(impulse, 0, pos.x, pos.y, true);
    }

    if (waiting && !actor.inAir && Math.abs(vel.len2()) <= 0) {
      actor.setSequence(IDLE.getName());
    }
  }

  Sequence<ActorComponent> IDLE = Sequence.<ActorComponent>builder("Idle", Assets.catIdle)
      .tags(SequenceTags.IDLE)
      .update((actor, delta) -> {
        Body body = actor.getComponent(PhysicsComponent.class).body;

        updateNav(actor);

        Vector2 vel = body.getLinearVelocity();
        if (Math.abs(vel.x) > 0) {
          actor.setAnimSpeed((Math.abs(vel.x) / 5) + 0.5f);
          actor.setSequence(CatSequences.WALK.getName());
        }
      })
      .build();

  Sequence<ActorComponent> WALK = Sequence.<ActorComponent>builder("Walk", Assets.catWalk)
      .tags(SequenceTags.WALKING)
      .update((actor, delta) -> updateNav(actor))
      .loop(true)
      .build();

  Sequence<ActorComponent> JUMP_LAND = Sequence.<ActorComponent>builder("JumpLand", Assets.catJumpEnd)
      .last((actor) -> {
        actor.setSequence(IDLE.getName());
      })
      .build();

  Sequence<ActorComponent> JUMP_IN_AIR = Sequence.<ActorComponent>builder("JumpInAir", Assets.catJumpIdle)
      .update((actor, delta) -> {
        if (!actor.inAir) {
          actor.setSequence(JUMP_LAND.getName());
        }
      })
      .loop(true)
      .build();

  Sequence<ActorComponent> JUMP = Sequence.<ActorComponent>builder("Jump", Assets.catJumpBegin)
      .frame((actor, frame) -> {
        if (frame == 0) {
          actor.setAnimSpeed(0);
        }
      })
      .last((actor) -> {
        TransformComponent transform = actor.getComponent(TransformComponent.class);
        Body body = actor.getComponent(PhysicsComponent.class).body;
        body.applyLinearImpulse(0f, 4f, transform.pos.x, transform.pos.y, true);
        actor.setSequence(JUMP_IN_AIR.getName());
      })
      .update((actor, delta) -> {
        if (actor.getAbsoluteElapsed() >= 0.1f) {
          actor.setAnimSpeed(1);
        }
      })
      .build();


    Sequence<ActorComponent> ATTACK_STRAIGHT = Sequence.<ActorComponent>builder("AttackStraight", Assets.catAttackStraight)
        .frame((actor, frame) -> {
          switch (frame) {
            case 1:
              actor.checkCollision((entity) -> {

              });
              break;
          }
        })
        .exit((actor) -> {

        })
        .last((actor) -> {
          actor.setSequence(IDLE.getName());
        })
        .build();

  Sequence<ActorComponent> ATTACK_JAB = Sequence.<ActorComponent>builder("AttackJab", Assets.catAttackJab)
      .frame((actor, frame) -> {
        Body body = actor.getComponent(PhysicsComponent.class).body;

        switch (frame) {
          case 0:
          case 1:
            if (Math.abs(body.getLinearVelocity().x) < 1f) {
              float impulse = 0;
              TransformComponent transform = actor.getComponent(TransformComponent.class);
              if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) && transform.facingLeft) {
                impulse = -5f;
              } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) && !transform.facingLeft) {
                impulse = 5f;
              }
              if (Math.abs(impulse) > 0) {
                body.applyLinearImpulse(impulse, 0f, transform.pos.x, transform.pos.y, true);
              }
            }
            break;
          case 2:
            if (Gdx.input.isKeyPressed(Input.Keys.X)) {
              actor.setSequence(ATTACK_STRAIGHT.getName());
            }
            break;
        }
      })
      .last((actor) -> {
        actor.setSequence(IDLE.getName());
      })
      .build();

}
