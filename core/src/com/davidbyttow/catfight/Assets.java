package com.davidbyttow.catfight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.davidbyttow.catfight.framework.animation.Animation;
import com.davidbyttow.catfight.framework.aseprite.FrameData;
import com.davidbyttow.catfight.framework.aseprite.SpriteAnimations;
import com.davidbyttow.catfight.framework.aseprite.SpriteSheetData;
import com.davidbyttow.catfight.framework.common.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.function.Function;

public final class Assets {

  public static Texture forestBackgroundTexture;
  public static TextureRegion forestBackgroundRegion;

  public static Texture groundTileTexture;
  public static TextureRegion groundTileRegion;

  public static Animation catIdle;
  public static Animation catWalk;
  public static Animation catJumpBegin;
  public static Animation catJumpIdle;
  public static Animation catJumpEnd;
  public static Animation catAttackJab;
  public static Animation catAttackStraight;

  public static void load() {
    forestBackgroundTexture = loadTexture("bg_forest01.png");
    forestBackgroundRegion = loadRegion(forestBackgroundTexture);

    groundTileTexture = loadTexture("grass01.png");
    groundTileRegion = loadRegion(groundTileTexture);

    Texture catTexture = loadTexture("cat.png");
    SpriteSheetData catSprite = loadSpriteSheet("cat.json");
    catIdle = SpriteAnimations.loadFromTag(catSprite, "idle", newRegionGenerator(catTexture));
    catWalk = SpriteAnimations.loadFromTag(catSprite, "walk", newRegionGenerator(catTexture));

    Texture catJumpTexture = loadTexture("cat_jump.png");
    SpriteSheetData catJumpSprite = loadSpriteSheet("cat_jump.json");
    catJumpBegin = SpriteAnimations.loadFromTag(catJumpSprite, "jump_begin", newRegionGenerator(catJumpTexture));
    catJumpIdle = SpriteAnimations.loadFromTag(catJumpSprite, "jump_idle", newRegionGenerator(catJumpTexture));
    catJumpEnd = SpriteAnimations.loadFromTag(catJumpSprite, "jump_end", newRegionGenerator(catJumpTexture));

    Texture catAttackTexture = loadTexture("cat_attack.png");
    SpriteSheetData catAttackSprite = loadSpriteSheet("cat_attack.json");
    catAttackJab = SpriteAnimations.loadFromTag(catAttackSprite, "attack_jab", newRegionGenerator(catAttackTexture));
    catAttackStraight = SpriteAnimations.loadFromTag(catAttackSprite, "attack_straight", newRegionGenerator(catAttackTexture));
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
