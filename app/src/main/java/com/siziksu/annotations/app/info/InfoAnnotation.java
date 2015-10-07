package com.siziksu.annotations.app.info;

import android.support.annotation.NonNull;

import com.siziksu.annotations.app.annotation.Info;

public class InfoAnnotation {

  private static InfoAnnotation instance;

  private InfoAnnotation() {

  }

  public static InfoAnnotation getInstance() {
    if (instance == null) {
      instance = new InfoAnnotation();
    }
    return instance;
  }

  public String getInfo(Object object) {
    if (object == null) {
      throw new NullPointerException("Object must not be null.");
    }
    Class<?> clazz = object.getClass();
    return getInfo(clazz);
  }

  public String getInfo(Class<?> clazz) {
    if (clazz == null) {
      throw new NullPointerException("Object must not be null.");
    }
    return info(clazz) + "</CLASS>";
  }

  private String info(@NonNull Class<?> clazz) {
    StringBuilder builder = new StringBuilder();
    builder.append(String.format("<CLASS name=\"%s\n", clazz.getSimpleName() + "\">"));
    if (clazz.isAnnotationPresent(Info.class)) {
      //      Annotation annotation = obj.getAnnotation(Info.class);
      //      Info testerInfo = (Info) annotation;
      Info info = clazz.getAnnotation(Info.class);
      builder.append(String.format("Priority: %s\n", info.priority()));
      builder.append(String.format("Created By: %s\n", info.createdBy()));
      builder.append("Tags: ");
      int tagLength = info.tags().length;
      for (String tag : info.tags()) {
        if (tagLength > 1) {
          builder.append(tag);
          builder.append(", ");
        } else {
          builder.append(tag);
        }
        tagLength--;
      }
      builder.append("\n");
      builder.append(String.format("Last Modified: %s\n", info.lastModified()));
    }
    return builder.toString();
  }
}
