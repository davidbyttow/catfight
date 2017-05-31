package com.davidbyttow.catfight.scripts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.davidbyttow.catfight.components.ActorComponent;
import com.davidbyttow.catfight.components.PhysicsComponent;
import com.davidbyttow.catfight.framework.animation.SequenceComponent;
import com.davidbyttow.catfight.framework.physics.Contacts;
import com.davidbyttow.catfight.framework.script.AbstractEntityScript;

public class ActorScript extends AbstractEntityScript {

  @Override public void update(float delta) {
    ActorComponent actor = getComponent(ActorComponent.class);
    SequenceComponent sequence = getComponent(SequenceComponent.class);
    PhysicsComponent physics = getComponent(PhysicsComponent.class);
    Body body = physics.body;

    boolean inAir = true;
    for (Contact contact : body.getWorld().getContactList()) {
      // Hack for now
      if (contact.isTouching() && Contacts.containsEntity(contact, getEntity())) {
        inAir = false;
      }
    }
    actor.inAir = true;

    Vector2 vel = body.getLinearVelocity();

    if (!inAir && Math.abs(vel.len2()) <= 0) {
      sequence.setSequence(actor.idleSequence.getName());
    }
  }
}
