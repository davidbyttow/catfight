package com.davidbyttow.catfight.components;

import com.badlogic.ashley.core.Component;
import com.davidbyttow.catfight.scripts.Script;

import java.util.ArrayList;
import java.util.List;

public class ScriptComponent implements Component {
  public List<Script> scripts = new ArrayList<>();
}
