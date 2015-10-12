package com.siziksu.annotations.data.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Info {

  enum Priority {
    LOW, MEDIUM, HIGH
  }

  Priority priority() default Priority.MEDIUM;

  String createdBy() default "Esteban";

  String contextDescription() default "";

  String lastModified() default "01/01/2015";

  String[] tags() default "";
}