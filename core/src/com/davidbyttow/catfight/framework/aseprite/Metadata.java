package com.davidbyttow.catfight.framework.aseprite;

import java.util.ArrayList;
import java.util.List;

public class Metadata {
  public String app;
  public String version;
  public String image;
  public String format;
  public FrameRect size;
  public float scale;
  public List<FrameTag> frameTags = new ArrayList<>();
}
