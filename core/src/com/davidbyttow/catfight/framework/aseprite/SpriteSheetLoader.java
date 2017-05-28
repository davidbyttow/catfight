package com.davidbyttow.catfight.framework.aseprite;

import com.davidbyttow.catfight.framework.json.Json;

import java.io.Reader;

public class SpriteSheetLoader {
  public SpriteSheetData load(Reader reader) {
    return Json.readObject(reader, SpriteSheetData.class);
  }
}
