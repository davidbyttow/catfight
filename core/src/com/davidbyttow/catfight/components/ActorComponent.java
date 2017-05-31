package com.davidbyttow.catfight.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.davidbyttow.catfight.framework.animation.Sequence;

public class ActorComponent implements Component {
  public Sequence<Entity> idleSequence;
  public boolean inAir;
}
