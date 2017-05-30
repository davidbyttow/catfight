package com.davidbyttow.catfight.scripts;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.davidbyttow.catfight.Assets;
import com.davidbyttow.catfight.components.AnimationComponent;
import com.davidbyttow.catfight.components.PhysicsComponent;
import com.davidbyttow.catfight.framework.animation.Sequence;
import com.davidbyttow.catfight.framework.animation.SequenceComponent;
import com.davidbyttow.catfight.framework.animation.SequenceScript;

public final class CatSequences {
  public static final Sequence<TextureRegion> IDLE = Sequence.create("idle", Assets.catIdle, new SequenceScript() {

    @Override public void enter(Entity entity) {
      System.out.println("enter idle");
    }

    @Override public void exit(Entity entity) {
      System.out.println("exit idle");
    }

    @Override public void update(Entity entity, float delta) {
      Body body = entity.getComponent(PhysicsComponent.class).body;
      AnimationComponent anim = entity.getComponent(AnimationComponent.class);
      SequenceComponent seq = entity.getComponent(SequenceComponent.class);

      Vector2 vel = body.getLinearVelocity();
      if (Math.abs(vel.x) > 0) {
        anim.animSpeed = (Math.abs(vel.x) / 5) + 0.5f;
        seq.setSequence(CatSequences.WALK.getName());
      }
    }

    @Override public boolean loop(Entity e) {
      return true;
    }
  });

  public static final Sequence<TextureRegion> WALK = Sequence.create("walk", Assets.catWalk, new SequenceScript() {
    @Override public boolean loop(Entity e) {
      return true;
    }
  });

  private CatSequences() {}
}
