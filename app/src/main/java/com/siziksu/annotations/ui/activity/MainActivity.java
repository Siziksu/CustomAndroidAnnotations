package com.siziksu.annotations.ui.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.siziksu.annotations.R;
import com.siziksu.annotations.ui.object.Index;
import com.siziksu.annotations.ui.object.adapter.IndexAdapter;
import com.siziksu.annotations.app.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {

  private List<Index> index;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    index = new ArrayList<Index>();
    index.add(new Index(
            InfoActivity.class,
            "Class annotations",
            "Simple example of Class annotations.")
    );
    index.add(new Index(
            CheckMethodActivity.class,
            "Method annotations",
            "Simple example of Method annotations.")
    );
    index.add(new Index(
            BusActivity.class,
            "More on method annotations",
            "Example of Method annotations with a Otto Event Bus simulation.")
    );
    index.add(new Index(
            ServiceActivity.class,
            "Method and Parameter annotations",
            "Example of Method and Parameter annotations with a Retrofit simulation.")
    );
    IndexAdapter adapter = new IndexAdapter(this, index);
    setListAdapter(adapter);
    getListView().setOnItemClickListener(this);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    startActivity(
        new Intent(this, index.get(position).clazz)
            .putExtra(Constants.EXTRAS_TITLE, index.get(position).title)
            .putExtra(Constants.EXTRAS_SUMMARY, index.get(position).summary)
    );
  }
}
