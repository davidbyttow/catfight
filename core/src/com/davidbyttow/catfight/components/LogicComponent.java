package com.davidbyttow.catfight.components;

import com.badlogic.ashley.core.Component;

import java.util.ArrayList;
import java.util.List;

public class LogicComponent implements Component {
  public interface Handler {
    void update(float delta);
  }

  public List<Handler> handlers = new ArrayList<>();
}
