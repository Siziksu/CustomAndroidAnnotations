package com.siziksu.annotations.data.service;

import com.siziksu.annotations.data.annotation.GET;
import com.siziksu.annotations.data.annotation.Query;
import com.siziksu.annotations.commons.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class RestAdapter {

  private final String url;

  private RestAdapter(Builder builder) {
    this.url = builder.url;
  }

  @SuppressWarnings("unchecked")
  public <T> T create(Class<T> clazz) {
    if (!clazz.isInterface()) {
      throw new IllegalArgumentException("Only interfaces are supported.");
    }
    if (clazz.getInterfaces().length > 0) {
      throw new IllegalArgumentException("Interface definitions must not extend other interfaces.");
    }
    try {
      Object instance = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new LocalInvocationHandler());
      return (T) instance;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public class LocalInvocationHandler implements InvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      Logger.println("<QUERY>");
      StringBuilder mainBuilder = new StringBuilder();
      StringBuilder parameterBuilder = new StringBuilder();
      if (method.isAnnotationPresent(GET.class)) {
        mainBuilder.append(url);
        mainBuilder.append(method.getAnnotation(GET.class).value());
        printParametersInfo(args, method);
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        boolean parameterWithAnnotationAdded = false;
        int index = 0;
        for (Annotation[] annotations : parameterAnnotations) {
          if (annotations.length > 0) {
            Annotation annotation = annotations[0];
            printAnnotationInfo(index, annotation);
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType == Query.class) {
              if (args[index] != null) {
                parameterWithAnnotationAdded = true;
                Query query = (Query) annotation;
                parameterBuilder.append(query.value());
                parameterBuilder.append("=");
                parameterBuilder.append(args[index].toString());
                parameterBuilder.append("&");
              }
            }
          }
          index++;
        }
        if (parameterWithAnnotationAdded) {
          parameterBuilder.deleteCharAt(parameterBuilder.length() - 1);
          mainBuilder.append("?");
          mainBuilder.append(parameterBuilder.toString());
        }
        Logger.println("Query invoked: " + mainBuilder.toString());
      } else {
        Logger.println("The method " + method + " needs the GET annotation.");
      }
      Logger.println("</QUERY>");
      return mainBuilder.length() > 0 ? mainBuilder.toString() : null;
    }
  }

  private void printParametersInfo(Object[] args, Method method) {
    Class<?>[] parameters = method.getParameterTypes();
    int count = args.length;
    Logger.println("<ARGUMENTS size=\"" + count + "\" />");
    for (int i = 0; i < parameters.length; i++) {
      Logger.println("<PARAMETER index=\"" + (i + 1) + "\" type=\"" + parameters[i].getSimpleName() + "\" value=\"" + args[i].toString() + "\" />");
    }
  }

  private void printAnnotationInfo(int index, Annotation annotation) {
    Logger.println("<ANNOTATION index=\"" + (index + 1) + "\" name=\"" + annotation.toString() + "\" />");
  }

  public static class Builder {

    private String url;

    public Builder setEndpoint(String url) {
      this.url = url;
      return this;
    }

    public RestAdapter build() {
      if (url == null) {
        throw new IllegalArgumentException("URL must not be null.");
      }
      return new RestAdapter(this);
    }
  }
}
