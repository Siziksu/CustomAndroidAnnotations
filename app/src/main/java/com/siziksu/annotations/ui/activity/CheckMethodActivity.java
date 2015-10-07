package com.siziksu.annotations.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.siziksu.annotations.R;
import com.siziksu.annotations.app.annotation.CheckMethod;
import com.siziksu.annotations.app.check_method.CheckMethodAnnotation;
import com.siziksu.annotations.app.mock.CheckMethodFakeClass;

public class CheckMethodActivity extends Activity {

  private TextView result;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_text_result);
    result = (TextView) findViewById(R.id.result);
  }

  @Override
  protected void onResume() {
    super.onResume();
    StringBuilder text = new StringBuilder();

    String info = CheckMethodAnnotation.getInstance().getInfo(this);
    text.append(text.length() > 0 ? "\n\n" + info : info);
    info = CheckMethodAnnotation.getInstance().getInfo(CheckMethodFakeClass.class);
    text.append(text.length() > 0 ? "\n\n" + info : info);

    result.setText(text);
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  @CheckMethod(false)
  void method1() {
    throw new RuntimeException("Ignored"); // This method won't be checked.
  }

  @CheckMethod
  void method2() {
    throw new RuntimeException("Fail"); // This method will be checked.
  }

  @CheckMethod
  void method3() {
    // This method will be checked.
  }
}