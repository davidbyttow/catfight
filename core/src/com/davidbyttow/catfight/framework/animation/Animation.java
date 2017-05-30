package com.davidbyttow.catfight.framework.animation;

import com.davidbyttow.catfight.framework.common.Streams;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class Animation {

  private final ImmutableList<KeyFrame> keyFrames;
  private final float duration;

  public Animation(List<KeyFrame> keyFrames) {
    this.keyFrames = ImmutableList.copyOf(keyFrames);
    this.duration = Streams.reduceToFloat(keyFrames.stream(), KeyFrame::getDuration);
    Preconditions.checkState(duration > 0);
  }

  public List<KeyFrame> getKeyFrames() {
    return keyFrames;
  }

  public float getDuration() {
    return duration;
  }

  public KeyFrame getLastKeyFrame() {
    return keyFrames.get(keyFrames.size() - 1);
  }

  public KeyFrame getKeyFrame(float time) {
    float t = time % duration;
    float endTime = 0;
    for (KeyFrame k : keyFrames) {
      endTime += k.getDuration();
      if (t < endTime) {
        return k;
      }
    }
    return keyFrames.get(keyFrames.size() - 1);
  }
}
