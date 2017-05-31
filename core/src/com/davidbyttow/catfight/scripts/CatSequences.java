package com.davidbyttow.catfight.scripts;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.davidbyttow.catfight.Assets;
import com.davidbyttow.catfight.components.AnimationComponent;
import com.davidbyttow.catfight.components.PhysicsComponent;
import com.davidbyttow.catfight.components.TransformComponent;
import com.davidbyttow.catfight.framework.animation.Sequence;
import com.davidbyttow.catfight.framework.animation.SequenceComponent;
import com.davidbyttow.catfight.framework.physics.Contacts;

public interface CatSequences {

  static void updateNav(Entity entity) {
    TransformComponent transform = entity.getComponent(TransformComponent.class);
    Body body = entity.getComponent(PhysicsComponent.class).body;
    SequenceComponent seq = entity.getComponent(SequenceComponent.class);

    if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
      seq.setSequence(JUMP.getName());
      return;
    }

    Vector2 vel = body.getLinearVelocity();
    Vector2 pos = body.getPosition();

    float impulse = 0;

    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
      impulse = -0.8f;
      transform.facingLeft = true;
    } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
      impulse = 0.8f;
      transform.facingLeft = false;
    }

    if (Math.abs(impulse) > 0 && Math.abs(vel.x) < 5f) {
      body.applyLinearImpulse(impulse, 0, pos.x, pos.y, true);
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
        PhysicsComponent physics = entity.getComponent(PhysicsComponent.class);
        SequenceComponent seq = entity.getComponent(SequenceComponent.class);
        Body body = physics.body;

        // Hack for now
        boolean inAir = true;
        for (Contact contact : body.getWorld().getContactList()) {
          if (contact.isTouching() && Contacts.containsEntity(contact, entity)) {
            inAir = false;
          }
        }
        if (!inAir) {
          seq.setSequence(JUMP_LAND.getName());
        }
      })
      .loop(true)
      .build();

  Sequence<Entity> JUMP = Sequence.<Entity>builder("Jump", Assets.catJumpBegin)
      .enter((entity) -> {
        TransformComponent transform = entity.getComponent(TransformComponent.class);
        Body body = entity.getComponent(PhysicsComponent.class).body;
        body.applyLinearImpulse(0f, 4f, transform.pos.x, transform.pos.y, true);
      })
      .last((entity) -> {
        SequenceComponent seq = entity.getComponent(SequenceComponent.class);
        seq.setSequence(JUMP_IN_AIR.getName());
      })
      .build();
}
