package com.siziksu.annotations.commons;

import android.util.Log;

public class Logger {

  private static final String TAG = "LOGGER";
  private static final boolean DEBUG = true;

  public static void println(Object o) {
    if (DEBUG) {
      print(String.valueOf(o));
    }
  }

  public static void printErrorLn(Object o) {
    if (DEBUG) {
      printError(String.valueOf(o));
    }
  }

  private static synchronized void print(String str) {
    str = str == null ? "null" : str;
    Log.d(TAG, str);
  }

  private static synchronized void printError(String str) {
    str = str == null ? "null" : str;
    Log.wtf(TAG, str);
  }
}