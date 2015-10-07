package com.siziksu.annotations.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.siziksu.annotations.R;
import com.siziksu.annotations.app.annotation.Info;
import com.siziksu.annotations.app.info.InfoAnnotation;
import com.siziksu.annotations.app.mock.InfoFakeClass;

@Info(
    priority = Info.Priority.HIGH,
    createdBy = "Esteban",
    contextDescription = "Info annotations",
    lastModified = "07/10/2015",
    tags = {"activity", "annotations", "info"})
public class InfoActivity extends Activity {

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

    String info = InfoAnnotation.getInstance().getInfo(this);
    text.append(text.length() > 0 ? "\n\n" + info : info);
    info = InfoAnnotation.getInstance().getInfo(InfoFakeClass.class);
    text.append(text.length() > 0 ? "\n\n" + info : info);

    result.setText(text);
  }

  @Override
  protected void onPause() {
    super.onPause();
  }
}