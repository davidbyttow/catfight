package com.davidbyttow.catfight.components;


import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.davidbyttow.catfight.framework.animation.Animation;

import java.util.HashMap;
import java.util.Map;

public class AnimationComponent implements Component {
  public String animName;
  public float animTime;
  public Map<String, Animation<TextureRegion>> animations = new HashMap<>();
}
