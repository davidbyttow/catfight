package com.davidbyttow.catfight.framework.common;

import com.badlogic.gdx.math.Vector2;

public final class Vectors {
  public static Vector2 scale(Vector2 v, float scale) {
    return new Vector2(v).scl(scale);
  }

  public static Vector2 scale(Vector2 v, Vector2 scale) {
    return new Vector2(v).scl(scale);
  }

  private Vectors() {}
}
