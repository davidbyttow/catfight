package com.davidbyttow.catfight.framework.physics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

public final class Contacts {
  public static boolean containsEntity(Contact contact, Entity entity) {
    return isEntity(contact.getFixtureA(), entity) || isEntity(contact.getFixtureB(), entity);
  }

  public static boolean isEntity(Fixture fixture, Entity entity) {
    return fixture.getBody().getUserData() == entity;
  }
}
