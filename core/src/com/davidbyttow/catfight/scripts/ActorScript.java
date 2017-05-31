package com.davidbyttow.catfight.scripts;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.davidbyttow.catfight.components.ActorComponent;
import com.davidbyttow.catfight.components.PhysicsComponent;
import com.davidbyttow.catfight.framework.physics.Contacts;
import com.davidbyttow.catfight.framework.script.AbstractEntityScript;

public class ActorScript extends AbstractEntityScript {

  @Override public void update(float delta) {
    ActorComponent actor = getComponent(ActorComponent.class);
    PhysicsComponent physics = getComponent(PhysicsComponent.class);
    Body body = physics.body;

    actor.inAir = true;
    for (Contact contact : body.getWorld().getContactList()) {
      // Hack for now
      if (contact.isTouching() && Contacts.containsEntity(contact, getEntity())) {
        actor.inAir = false;
        break;
      }
    }
  }
}
