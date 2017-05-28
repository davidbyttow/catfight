package com.davidbyttow.gdxtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public final class Assets {

  public static Texture catTexture;

  public static Animation<TextureRegion> catIdle;

  public static Texture loadTexture(String file) {
    return new Texture(Gdx.files.internal(file));
  }

  public static void load() {
    catTexture = loadTexture("core/assets/cat_idle.png");
    catIdle = new Animation<TextureRegion>(
        1, loadRegions(catTexture, 0, 1, 2, 3), Animation.PlayMode.LOOP);
  }

  private static Array<TextureRegion> loadRegions(Texture texture, int ...indices) {
    Array<TextureRegion> regions = new Array<TextureRegion>();
    for (int index : indices) {
      TextureRegion region = new TextureRegion(texture, index * 32, 0, 32, 32);
      regions.add(region);
    }
    return regions;
  }

  private Assets() {}
}
