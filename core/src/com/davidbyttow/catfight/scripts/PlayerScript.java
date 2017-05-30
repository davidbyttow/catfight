package com.davidbyttow.catfight.scripts;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.davidbyttow.catfight.components.PhysicsComponent;
import com.davidbyttow.catfight.components.TransformComponent;
import com.davidbyttow.catfight.framework.animation.SequenceComponent;
import com.davidbyttow.catfight.framework.physics.Contacts;
import com.davidbyttow.catfight.framework.script.AbstractEntityScript;

public class PlayerScript extends AbstractEntityScript {

  @Override public boolean keyDown(int keyCode) {
    SequenceComponent sequence = getComponent(SequenceComponent.class);
    if (keyCode == Input.Keys.SPACE) {
//      TransformComponent transform = getComponent(TransformComponent.class);
//      PhysicsComponent physics = getComponent(PhysicsComponent.class);
//      physics.body.applyLinearImpulse(0f, 4f, transform.pos.x, transform.pos.y, true);
    } else if (keyCode == Input.Keys.Z) {
//      SequenceComponent sequence = getComponent(SequenceComponent.class);
//      sequence.setSequence("kick");
    }
    return false;
  }

  @Override public void update(float delta) {
    SequenceComponent sequence = getComponent(SequenceComponent.class);
    PhysicsComponent physics = getComponent(PhysicsComponent.class);
    TransformComponent transform = getComponent(TransformComponent.class);
    Body body = physics.body;

    // Hack for now
    boolean inAir = true;
    for (Contact contact : body.getWorld().getContactList()) {
      if (contact.isTouching() && Contacts.containsEntity(contact, getEntity())) {
        inAir = false;
      }
    }

    Vector2 vel = body.getLinearVelocity();
    Vector2 pos = body.getPosition();

    float impulse = 0;

    if (!inAir && Math.abs(vel.len2()) <= 0) {
      sequence.setSequence(CatSequences.IDLE.getName());
    }
  }
}
