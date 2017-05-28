package com.davidbyttow.catfight.framework.animation;

import com.davidbyttow.catfight.framework.common.Streams;
import com.google.common.base.Preconditions;

import java.util.List;

public class Animation<T> {

  private final List<KeyFrame<T>> keyFrames;
  private final float duration;

  Animation(List<KeyFrame<T>> keyFrames) {
    this.keyFrames = keyFrames;
    this.duration = Streams.reduceToFloat(keyFrames.stream(), KeyFrame::getDuration);
    Preconditions.checkState(duration > 0);
  }


  public KeyFrame<T> getKeyFrame(float time) {
    // TODO(d): Support other play modes
    float t = time % duration;
    float endTime = 0;
    for (KeyFrame<T> k : keyFrames) {
      endTime += k.getDuration();
      if (t < endTime) {
        return k;
      }
    }
    // Just return the last frame
    return keyFrames.get(keyFrames.size() - 1);
  }
}
