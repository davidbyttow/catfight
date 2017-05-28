package com.davidbyttow.catfight.framework.aseprite;

import com.davidbyttow.catfight.framework.animation.Animation;
import com.davidbyttow.catfight.framework.animation.KeyFrame;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class SpriteAnimations {

  // TODO(d): Consider a separate interface wrapping the sheet data
  public static <T> Animation<T> loadFromTag(SpriteSheetData sheetData, String name, Function<FrameData, T> generator) {
    Preconditions.checkArgument(sheetData.frames.size() > 0);
    Preconditions.checkArgument(sheetData.meta != null);
    FrameTag tag = sheetData.meta.frameTags.stream()
        .filter(t -> t.name.equals(name))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("tag not found"));
    List<KeyFrame<T>> keyFrames = new ArrayList<>();
    for (int i = tag.from; i <= tag.to; ++i) {
      FrameData frame = sheetData.frames.get(i);
      T ref = generator.apply(frame);
      float duration = frame.duration / 1000f;
      KeyFrame<T> keyFrame = new KeyFrame<>(keyFrames.size(), duration, ref);
      keyFrames.add(keyFrame);
    }
    return new Animation<>(keyFrames);
  }

  private SpriteAnimations() {}
}
