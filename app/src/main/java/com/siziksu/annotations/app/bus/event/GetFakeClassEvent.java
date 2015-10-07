package com.siziksu.annotations.app.bus.event;

import com.siziksu.annotations.app.mock.BusFakeClass;

public class GetFakeClassEvent {

  public BusFakeClass busFakeClass;

  public GetFakeClassEvent(BusFakeClass busFakeClass) {
    this.busFakeClass = busFakeClass;
  }
}
