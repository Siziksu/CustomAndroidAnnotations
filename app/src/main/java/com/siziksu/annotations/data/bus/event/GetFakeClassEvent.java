package com.siziksu.annotations.data.bus.event;

import com.siziksu.annotations.commons.mock.BusFakeClass;

public class GetFakeClassEvent {

  public BusFakeClass busFakeClass;

  public GetFakeClassEvent(BusFakeClass busFakeClass) {
    this.busFakeClass = busFakeClass;
  }
}
