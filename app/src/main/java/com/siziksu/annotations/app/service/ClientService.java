package com.siziksu.annotations.app.service;

import com.siziksu.annotations.app.annotation.GET;
import com.siziksu.annotations.app.annotation.Query;

public interface ClientService {

  @GET("/search")
  String query(@Query("q") String query);

  @GET("/search")
  String query(@Query("q1") String query1, String query2, @Query("q3") String query3);
}
