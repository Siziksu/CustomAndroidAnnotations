package com.siziksu.annotations.data.service;

public class ServiceAdapter {

  private static final String BASE_URL = "https://www.google.com";

  private static ServiceAdapter serviceAdapter;
  private ClientService service;

  public static ServiceAdapter getInstance() {
    if (serviceAdapter == null) {
      serviceAdapter = new ServiceAdapter();
    }
    return serviceAdapter;
  }

  private RestAdapter getAdapter() {
    return new RestAdapter.Builder().setEndpoint(BASE_URL).build();
  }

  public ClientService getService() {
    if (service == null) {
      service = getAdapter().create(ClientService.class);
    }
    return service;
  }
}
