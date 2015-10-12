package com.siziksu.annotations.data.service;

import com.siziksu.annotations.data.annotation.GET;
import com.siziksu.annotations.data.annotation.Query;

public interface ClientService {

  @GET("/search")
  String query(@Query("q") String query);

  @GET("/search")
  String query(@Query("q1") String query1, String query2, @Query("q3") String query3);
}
