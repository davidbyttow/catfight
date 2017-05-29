package com.davidbyttow.catfight.scripts;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

public interface Script {
  void onAdded(Engine engine, Entity entity);

  void onRemoved();

  void update(float delta);

  <T extends Component> T getComponent(Class<T> type);
}
