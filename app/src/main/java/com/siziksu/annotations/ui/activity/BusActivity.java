package com.siziksu.annotations.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.siziksu.annotations.R;
import com.siziksu.annotations.data.bus.Bus;
import com.siziksu.annotations.data.bus.Subscribe;
import com.siziksu.annotations.data.bus.event.GetFakeClassEvent;
import com.siziksu.annotations.data.bus.event.GetIntegerEvent;
import com.siziksu.annotations.data.bus.event.GetStringsEvent;
import com.siziksu.annotations.commons.mock.BusFakeClass;

import java.util.ArrayList;

public class BusActivity extends Activity implements View.OnClickListener {

  private TextView result;
  private RelativeLayout content;
  private BusFakeClass busFakeClass;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_text_result);
    busFakeClass = new BusFakeClass();
    result = (TextView) findViewById(R.id.result);
    content = (RelativeLayout) findViewById(R.id.content);
  }

  @Override
  protected void onResume() {
    super.onResume();
    result.setText(getString(R.string.touch_screen));
    result.setOnClickListener(this);
    content.setOnClickListener(this);
    Bus.getInstance().register(this);
    busFakeClass.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    Bus.getInstance().unregister(this);
    busFakeClass.onPause();
  }

  @Subscribe
  public void busActivityMethod1(GetStringsEvent event) {
    String string = "List of strings:";
    result.append(result.getText().equals("") ? string : "\n" + string);
    int counter = 0;
    for (String s : event.strings) {
      result.append("\n" + ++counter + ". " + s);
    }
  }

  @Subscribe
  public void busActivityMethod2(GetIntegerEvent event) {
    String string = "The number is " + event.integer + " (this is fired in HomeActivity)";
    result.append(result.getText().equals("") ? string : "\n" + string);
  }

  @Subscribe
  public void busActivityMethod3(GetFakeClassEvent event) {
    String string = event.busFakeClass.toString() + " (this is fired in HomeActivity)";
    result.append(result.getText().equals("") ? string : "\n" + string);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      default:
        result.setText("");
        ArrayList<String> list = new ArrayList<>();
        list.add("ONE");
        list.add("TWO");
        list.add("THREE");
        Bus.getInstance().send(new GetStringsEvent(list));
        Bus.getInstance().send(new GetIntegerEvent(1894));
        Bus.getInstance().send(new GetFakeClassEvent(busFakeClass));

        Toast.makeText(this, "There are more logs in the logcat", Toast.LENGTH_SHORT).show();
        break;
    }
    content.setOnClickListener(null);
    result.setOnClickListener(null);
  }
}