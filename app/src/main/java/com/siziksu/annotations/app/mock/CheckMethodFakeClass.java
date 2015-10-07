package com.siziksu.annotations.app.mock;

import com.siziksu.annotations.app.annotation.CheckMethod;

public class CheckMethodFakeClass {

  @CheckMethod
  void method1() {
    // This method will be checked.
  }

  @CheckMethod
  void method2() {
    throw new RuntimeException("Fail"); // This method will be checked.
  }

  @CheckMethod(false)
  void method3() {
    throw new RuntimeException("Ignored"); // This method won't be checked.
  }

  @CheckMethod(true)
  void method4() {
    // This method will be checked.
  }
}