package com.davidbyttow.catfight.framework.input;

import org.joda.time.DateTime;

public class InputEvent {
  public InputType type;
  public int keyCode;
  public char key;
  DateTime time;

  public DateTime getTime() {
    return time;
  }
}
