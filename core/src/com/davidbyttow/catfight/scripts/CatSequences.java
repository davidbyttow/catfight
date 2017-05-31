package com.davidbyttow.catfight.scripts;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.davidbyttow.catfight.Assets;
import com.davidbyttow.catfight.components.ActorComponent;
import com.davidbyttow.catfight.components.AnimationComponent;
import com.davidbyttow.catfight.components.PhysicsComponent;
import com.davidbyttow.catfight.components.TransformComponent;
import com.davidbyttow.catfight.framework.animation.Sequence;
import com.davidbyttow.catfight.framework.animation.SequenceComponent;

public interface CatSequences {

  static void updateNav(Entity entity) {
    ActorComponent actor = entity.getComponent(ActorComponent.class);
    TransformComponent transform = entity.getComponent(TransformComponent.class);
    Body body = entity.getComponent(PhysicsComponent.class).body;
    SequenceComponent seq = entity.getComponent(SequenceComponent.class);

    boolean waiting = seq.hasTag(SequenceTags.IDLE) || seq.hasTag(SequenceTags.WALKING);

    if (waiting) {
      if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
        seq.setSequence(JUMP.getName());
        waiting = false;
      } else if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
        seq.setSequence(ATTACK_JAB.getName());
        waiting = false;
      } else if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
        seq.setSequence(ATTACK_STRAIGHT.getName());
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
      seq.setSequence(IDLE.getName());
    }
  }

  Sequence<Entity> IDLE = Sequence.<Entity>builder("Idle", Assets.catIdle)
      .tags(SequenceTags.IDLE)
      .update((entity, delta) -> {
        Body body = entity.getComponent(PhysicsComponent.class).body;
        AnimationComponent anim = entity.getComponent(AnimationComponent.class);
        SequenceComponent seq = entity.getComponent(SequenceComponent.class);

        updateNav(entity);

        Vector2 vel = body.getLinearVelocity();
        if (Math.abs(vel.x) > 0) {
          anim.animSpeed = (Math.abs(vel.x) / 5) + 0.5f;
          seq.setSequence(CatSequences.WALK.getName());
        }
      })
      .build();

  Sequence<Entity> WALK = Sequence.<Entity>builder("Walk", Assets.catWalk)
      .tags(SequenceTags.WALKING)
      .update((entity, delta) -> updateNav(entity))
      .loop(true)
      .build();

  Sequence<Entity> JUMP_LAND = Sequence.<Entity>builder("JumpLand", Assets.catJumpEnd)
      .last((entity) -> {
        SequenceComponent seq = entity.getComponent(SequenceComponent.class);
        seq.setSequence(IDLE.getName());
      })
      .build();

  Sequence<Entity> JUMP_IN_AIR = Sequence.<Entity>builder("JumpInAir", Assets.catJumpIdle)
      .update((entity, delta) -> {
        ActorComponent actor = entity.getComponent(ActorComponent.class);
        SequenceComponent seq = entity.getComponent(SequenceComponent.class);
        if (!actor.inAir) {
          seq.setSequence(JUMP_LAND.getName());
        }
      })
      .loop(true)
      .build();

  Sequence<Entity> JUMP = Sequence.<Entity>builder("Jump", Assets.catJumpBegin)
      .update((entity, delta) -> {
        SequenceComponent seq = entity.getComponent(SequenceComponent.class);
        if (seq.getElapsed() >= 0.05f) {
          TransformComponent transform = entity.getComponent(TransformComponent.class);
          Body body = entity.getComponent(PhysicsComponent.class).body;
          body.applyLinearImpulse(0f, 4f, transform.pos.x, transform.pos.y, true);
          seq.setSequence(JUMP_IN_AIR.getName());
        }
      })
      .build();


  Sequence<Entity> ATTACK_STRAIGHT = Sequence.<Entity>builder("AttackStraight", Assets.catAttackStraight)
      .last((entity) -> {
        SequenceComponent seq = entity.getComponent(SequenceComponent.class);
        seq.setSequence(IDLE.getName());
      })
      .build();

  Sequence<Entity> ATTACK_JAB = Sequence.<Entity>builder("AttackJab", Assets.catAttackJab)
      .frame((entity, frame) -> {
        Body body = entity.getComponent(PhysicsComponent.class).body;
        SequenceComponent seq = entity.getComponent(SequenceComponent.class);

        switch (frame) {
          case 0:
          case 1:
            if (Math.abs(body.getLinearVelocity().x) < 1f) {
              float impulse = 0;
              TransformComponent transform = entity.getComponent(TransformComponent.class);
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
              seq.setSequence(ATTACK_STRAIGHT.getName());
            }
            break;
        }
      })
      .last((entity) -> {
        SequenceComponent seq = entity.getComponent(SequenceComponent.class);
        seq.setSequence(IDLE.getName());
      })
      .build();

}
