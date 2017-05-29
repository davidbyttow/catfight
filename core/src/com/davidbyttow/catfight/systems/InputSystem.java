package com.davidbyttow.catfight.systems;

import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.InputProcessor;
import com.davidbyttow.catfight.components.InputComponent;
import com.davidbyttow.catfight.framework.common.SystemPriorities;
import com.davidbyttow.catfight.framework.input.CompositeInputProcessor;
import com.davidbyttow.catfight.framework.script.EntityScript;
import com.davidbyttow.catfight.framework.script.ScriptableSystem;

import java.util.function.Function;

public class InputSystem extends ScriptableSystem {

  private final InputProcessor inputProcessor = new InputProcessor() {
    @Override public boolean keyDown(int keycode) {
      return handle(s -> s.keyDown(keycode));
    }

    @Override public boolean keyUp(int keycode) {
      return handle(s -> s.keyUp(keycode));
    }

    @Override public boolean keyTyped(char character) {
      return handle(s -> s.keyTyped(character));
    }

    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
      return handle(s -> s.touchDown(screenX, screenY, pointer, button));
    }

    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
      return handle(s -> s.touchUp(screenX, screenY, pointer, button));
    }

    @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
      return handle(s -> s.touchDragged(screenX, screenY, pointer));
    }

    @Override public boolean mouseMoved(int screenX, int screenY) {
      return handle(s -> s.mouseMoved(screenX, screenY));
    }

    @Override public boolean scrolled(int amount) {
      return handle(s -> s.scrolled(amount));
    }
  };

  public InputSystem(CompositeInputProcessor input) {
    super(Family.all(InputComponent.class).get(), SystemPriorities.INPUT);
    input.addListener(inputProcessor);
  }

  @Override protected void handleScript(EntityScript script, float deltaTime) {
  }

  private boolean handle(Function<InputProcessor, Boolean> fn) {
    forEachScript(fn::apply);
    return false;
  }
}
