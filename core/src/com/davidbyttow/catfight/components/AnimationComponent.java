package com.davidbyttow.catfight.components;


import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;
import com.davidbyttow.catfight.framework.animation.Animation;

public class AnimationComponent implements Component {
  public IntMap<Animation<TextureRegion>> animations = new IntMap<>();
}
