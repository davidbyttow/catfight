package com.davidbyttow.catfight.components;

import com.badlogic.ashley.core.Component;
import com.davidbyttow.catfight.scripts.EntityScript;

import java.util.ArrayList;
import java.util.List;

public class ScriptComponent implements Component {
  public List<EntityScript> entityScripts = new ArrayList<>();
}
