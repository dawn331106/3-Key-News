package com.example.a3keynews;

import com.example.a3keynews.parameter.Headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Interface {
    @GET("everything")
    Call<Headlines>getHeadlines(
        @Query("qInTitle") String q,
              @Query("apikey") String apikey
    );
}
