package com.davidbyttow.catfight.framework.common;

public interface SystemPriorities {
  int FIRST = -200;

  int INPUT = -100;

  int PRE_TICK = -10;
  int TICK = 0;
  int POST_TICK = 10;

  int PRE_RENDER = 200;
  int RENDER = 210;
  int POST_RENDER = 220;

  int LAST = 999999;
}
