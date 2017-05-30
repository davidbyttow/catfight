package com.davidbyttow.catfight.framework.animation;

import com.davidbyttow.catfight.framework.common.Streams;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class Animation<T> {

  private final ImmutableList<KeyFrame<T>> keyFrames;
  private final float duration;

  public Animation(List<KeyFrame<T>> keyFrames) {
    this.keyFrames = ImmutableList.copyOf(keyFrames);
    this.duration = Streams.reduceToFloat(keyFrames.stream(), KeyFrame::getDuration);
    Preconditions.checkState(duration > 0);
  }

  public ImmutableList<KeyFrame<T>> getKeyFrames() {
    return keyFrames;
  }

  public float getDuration() {
    return duration;
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
    return keyFrames.get(keyFrames.size() - 1);
  }
}
