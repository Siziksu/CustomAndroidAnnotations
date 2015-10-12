package com.siziksu.annotations.data.check_method;

import android.support.annotation.NonNull;

import com.siziksu.annotations.data.annotation.CheckMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CheckMethodAnnotation {

  private static CheckMethodAnnotation instance;

  private int count;
  private int passed;
  private int failed;
  private int ignored;

  private CheckMethodAnnotation() {

  }

  public static CheckMethodAnnotation getInstance() {
    if (instance == null) {
      instance = new CheckMethodAnnotation();
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
    return test(clazz) + getResult() + "</CLASS>";
  }

  private String test(@NonNull Class<?> clazz) {
    int count = 0, passed = 0, failed = 0, ignored = 0;
    StringBuilder builder = new StringBuilder();
    builder.append(String.format("<CLASS name=\"%s\n", clazz.getSimpleName() + "\">"));
    for (Method method : clazz.getDeclaredMethods()) {
      if (method.isAnnotationPresent(CheckMethod.class)) {
        Annotation annotation = method.getAnnotation(CheckMethod.class);
        CheckMethod checkMethod = (CheckMethod) annotation;
        if (checkMethod.value()) {
          try {
            method.setAccessible(true);
            method.invoke(clazz.newInstance());
            builder.append(String.format("%s. Method \"%s\" passed\n", ++count, clazz.getSimpleName() + "." + method.getName() + "()"));
            passed++;
          } catch (Exception e) {
            builder.append(String.format("%s. Method \"%s\" failed\n", ++count, clazz.getSimpleName() + "." + method.getName() + "()"));
            failed++;
          }
        } else {
          builder.append(String.format("%s. Method \"%s\" ignored\n", ++count, clazz.getSimpleName() + "." + method.getName() + "()"));
          ignored++;
        }
      }
    }
    this.count = count;
    this.passed = passed;
    this.failed = failed;
    this.ignored = ignored;
    return builder.toString();
  }

  public String getResult() {
    return (String.format("<METHODS checked=\"%d\" passed=\"%d\" failed=\"%d\" ignored=\"%d\" />\n", count, passed, failed, ignored));
  }
}
