package com.davidbyttow.catfight.components;


import com.badlogic.ashley.core.Component;
import com.davidbyttow.catfight.framework.animation.Animation;

public class AnimationComponent implements Component {
  public float animTime;
  public float animSpeed = 1;
  public Animation anim;

  public void setAnim(Animation anim) {
    if (this.anim != anim) {
      this.animTime = 0;
      this.animSpeed = 1;
      this.anim = anim;
    }
  }
}
