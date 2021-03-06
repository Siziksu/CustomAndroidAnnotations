package com.siziksu.annotations.commons.mock;

import com.siziksu.annotations.data.bus.Bus;
import com.siziksu.annotations.data.bus.Subscribe;
import com.siziksu.annotations.data.bus.event.GetFakeClassEvent;
import com.siziksu.annotations.data.bus.event.GetIntegerEvent;
import com.siziksu.annotations.commons.Logger;

public class BusFakeClass {

  public BusFakeClass() {

  }

  public void onResume() {
    Bus.getInstance().register(this);
  }

  public void onPause() {
    Bus.getInstance().unregister(this);
  }

  @Subscribe
  public void fakeClassMethod1(GetIntegerEvent event) {
    Logger.println("The number is " + event.integer + " (this is fired in BusFakeClass)");
  }

  @Subscribe
  public void fakeClassMethod2(GetFakeClassEvent event) {
    Logger.println(event.busFakeClass.toString() + " (this is fired in BusFakeClass)");
  }

  @Override
  public String toString() {
    return "This is a fake class";
  }
}
