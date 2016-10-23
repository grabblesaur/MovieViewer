package com.bbayar.movieviewer.model.rest;


import com.bbayar.movieviewer.model.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("tv/on_the_air")
    Call<TvResponse> getTvResponse(@Query("api_key") String apiKey);
}
