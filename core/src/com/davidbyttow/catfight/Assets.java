package com.davidbyttow.catfight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.davidbyttow.catfight.framework.animation.Animation;
import com.davidbyttow.catfight.framework.aseprite.FrameData;
import com.davidbyttow.catfight.framework.aseprite.SpriteAnimations;
import com.davidbyttow.catfight.framework.aseprite.SpriteSheetData;
import com.davidbyttow.catfight.framework.json.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.function.Function;

public final class Assets {

  public static Texture forestBackgroundTexture;
  public static TextureRegion forestBackgroundRegion;

  public static Texture groundTileTexture;
  public static TextureRegion groundTileRegion;

  public static Texture catTexture;
  private static SpriteSheetData catSpriteData;

  public static Animation<TextureRegion> catIdle;
  public static Animation<TextureRegion> catWalk;

  public static void load() {
    forestBackgroundTexture = loadTexture("bg_forest01.png");
    forestBackgroundRegion = loadRegion(forestBackgroundTexture);

    groundTileTexture = loadTexture("grass01.png");
    groundTileRegion = loadRegion(groundTileTexture);

    catTexture = loadTexture("cat.png");
    catSpriteData = loadSpriteSheet("cat.json");
    catIdle = SpriteAnimations.loadFromTag(catSpriteData, "idle", newRegionGenerator(catTexture));
    catWalk = SpriteAnimations.loadFromTag(catSpriteData, "walk", newRegionGenerator(catTexture));
  }

  public static Texture loadTexture(String file) {
    return new Texture(Gdx.files.internal(file));
  }

  public static TextureRegion loadRegion(Texture texture) {
    return new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
  }

  public static SpriteSheetData loadSpriteSheet(String file) {
    return Json.readObject(loadFile(file), SpriteSheetData.class);
  }

  private static Function<FrameData, TextureRegion> newRegionGenerator(Texture texture) {
    return f -> new TextureRegion(texture, f.frame.x, f.frame.y, f.frame.w, f.frame.h);
  }

  private static FileReader loadFile(String file) {
    try {
      FileHandle fh = Gdx.files.internal(file);
      return new FileReader(fh.file());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private Assets() {}
}
