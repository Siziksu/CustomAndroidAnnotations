package com.siziksu.annotations.data.bus.event;

import java.util.List;

public class GetStringsEvent {

  public List<String> strings;

  public GetStringsEvent(List<String> strings) {
    this.strings = strings;
  }
}
