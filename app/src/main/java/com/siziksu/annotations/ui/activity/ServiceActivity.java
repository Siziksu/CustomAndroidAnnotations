package com.siziksu.annotations.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.siziksu.annotations.R;
import com.siziksu.annotations.data.service.ServiceAdapter;

public class ServiceActivity extends Activity {

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

    text.append("<QUERY>\n");
    text.append(ServiceAdapter.getInstance().getService().query("java_annotations_example"));
    text.append("\n</QUERY>");
    text.append("\n\n");
    text.append("<QUERY>\n");
    text.append(ServiceAdapter.getInstance().getService().query("parameter_one", "parameter_two", "parameter_three"));
    text.append("\n</QUERY>");
    result.setText(text);

    Toast.makeText(this, "Take a look at the logcat logs", Toast.LENGTH_SHORT).show();
  }
}