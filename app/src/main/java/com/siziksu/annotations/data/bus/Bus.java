package com.siziksu.annotations.data.bus;

import java.lang.reflect.Method;

public class Bus {

  private static Bus instance;

  private Bus() {}

  public static Bus getInstance() {
    if (instance == null) {
      instance = new Bus();
    }
    return instance;
  }

  public void register(Object listener) {
    if (listener == null) {
      throw new NullPointerException("Object to register must not be null.");
    }
    if (!BusHandler.CACHE.containsKey(listener.getClass())) {
      BusHandler.loadAnnotatedMethods(listener);
    }
  }

  public void unregister(Object listener) {
    if (listener == null) {
      throw new NullPointerException("Object to unregister must not be null.");
    }
    if (!BusHandler.CACHE.containsKey(listener.getClass())) {
      throw new IllegalArgumentException("Is " + listener.getClass() + " registered?");
    } else {
      BusHandler.CACHE.remove(listener.getClass());
    }
  }

  public void send(Object event) {
    if (event == null) {
      throw new NullPointerException("Event to post must not be null.");
    }
    for (Class<?> clazz : BusHandler.CACHE.keySet()) {
      for (Method method : BusHandler.CACHE.get(clazz).keySet()) {
        Class<?> parameter = BusHandler.CACHE.get(clazz).get(method);
        if (event.getClass() == parameter) {
          post(clazz, method, event);
        }
      }
    }
  }

  private void post(Class<?> clazz, Method method, Object event) {
    method.setAccessible(true);
    try {
      method.invoke(BusHandler.CLASS_CACHE.get(clazz), event); // clazz.newInstance()
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
