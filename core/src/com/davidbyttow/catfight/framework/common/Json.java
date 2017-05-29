package com.davidbyttow.catfight.framework.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;

public final class Json {
  private static final Gson GSON = new GsonBuilder().create();

  public static <T> T readObject(Reader reader, Class<T> type) {
    T obj = GSON.fromJson(reader, type);
    if (obj != null) {
      return obj;
    }
    try {
      return type.newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private Json() {}
}
