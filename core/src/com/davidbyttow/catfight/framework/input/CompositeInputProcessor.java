package com.davidbyttow.catfight.framework.input;

import com.badlogic.gdx.InputProcessor;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class CompositeInputProcessor implements InputProcessor {

  private Set<InputProcessor> processors = new HashSet<>();

  public void addListener(InputProcessor processor) {
    processors.add(processor);
  }

  public void removeListener(InputProcessor processor) {
    processors.remove(processor);
  }

  @Override public boolean keyDown(int keycode) {
    return process(p -> p.keyDown(keycode));
  }

  @Override public boolean keyUp(int keycode) {
    return process(p -> p.keyUp(keycode));
  }

  @Override public boolean keyTyped(char character) {
    return process(p -> p.keyTyped(character));
  }

  @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return process(p -> p.touchDown(screenX, screenY, pointer, button));
  }

  @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return process(p -> p.touchUp(screenX, screenY, pointer, button));
  }

  @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
    return process(p -> p.touchDragged(screenX, screenY, pointer));
  }

  @Override public boolean mouseMoved(int screenX, int screenY) {
    return process(p -> p.mouseMoved(screenX, screenY));
  }

  @Override public boolean scrolled(int amount) {
    return process(p -> p.scrolled(amount));
  }

  private boolean process(Function<InputProcessor, Boolean> fn) {
    return processors.stream().map(fn).reduce(false, (a, b) -> a || b);
  }
}
