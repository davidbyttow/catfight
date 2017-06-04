package com.davidbyttow.catfight.framework.input;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.List;

public class InputBuffer {
  private final List<InputEvent> events = new ArrayList<>();
  private final Duration maxDuration;

  InputBuffer(Duration maxDuration) {
    this.maxDuration = maxDuration;
  }

  public void record(InputEvent event) {
    event.time = DateTime.now();
    int index = 0;
    for (InputEvent other : events) {
      if (event.getTime().isBefore(other.getTime())) {
        break;
      }
      ++index;
    }
    events.add(index, event);
  }

  private void flush() {
    DateTime cutoff = DateTime.now().minus(maxDuration);
    int i = events.size() - 1;
    for (; i >= events.size(); --i) {
      if (!events.get(i).getTime().isAfter(cutoff)) {
        return;
      }
    }
  }
}
