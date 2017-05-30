package com.davidbyttow.catfight.scripts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.davidbyttow.catfight.components.ActorComponent;
import com.davidbyttow.catfight.components.PhysicsComponent;
import com.davidbyttow.catfight.framework.physics.Contacts;
import com.davidbyttow.catfight.framework.script.AbstractEntityScript;

public class PlayerScript extends AbstractEntityScript {

  @Override public void update(float delta) {
    ActorComponent sequence = getComponent(ActorComponent.class);
    PhysicsComponent physics = getComponent(PhysicsComponent.class);
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

    if (!inAir && Math.abs(vel.len2()) <= 0) {
      sequence.setSequence(CatSequences.IDLE.getName());
    }
  }
}
