package com.siziksu.annotations.data.bus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

class BusHandler {

  static final Map<Class<?>, Map<Method, Class<?>>> CACHE = new HashMap<>();
  static final Map<Class<?>, Object> CLASS_CACHE = new HashMap<>();

  static void loadAnnotatedMethods(Object listener) {
    Class<?> listenerClass = listener.getClass();
    Map<Method, Class<?>> subscriberMethods = new HashMap<>();
    boolean addToClassCache = false;
    for (Method method : listenerClass.getDeclaredMethods()) {
      if (method.isBridge()) { // Ignore synthetic bridge methods
        continue;
      }
      if (method.isAnnotationPresent(Subscribe.class)) {
        Class<?>[] parameters = method.getParameterTypes();
        if (parameters.length != 1) {
          throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation but requires a single argument.");
        }
        Class<?> parameter = parameters[0];
        if (parameter.isInterface()) {
          throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation on " + parameter + " which is an interface. Subscription must be on a concrete class type.");
        }
        if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
          throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation on " + parameter + " but is not 'public'.");
        }
        addToClassCache = true;
        subscriberMethods.put(method, parameter);
      }
    }
    if (addToClassCache) {
      CLASS_CACHE.put(listenerClass, listener);
    }
    CACHE.put(listenerClass, subscriberMethods);
  }
}
