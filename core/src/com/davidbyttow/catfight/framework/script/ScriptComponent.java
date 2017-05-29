package com.davidbyttow.catfight.framework.script;

import com.badlogic.ashley.core.Component;

import java.util.ArrayList;
import java.util.List;

public class ScriptComponent implements Component {
  public List<EntityScript> entityScripts = new ArrayList<>();
}
