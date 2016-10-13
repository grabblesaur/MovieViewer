package com.bbayar.movieviewer.model.rest;


import com.bbayar.movieviewer.model.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("tv/on_the_air/{api_key}")
    Call<TvResponse> getTvResponse(@Path("api_key") String apiKey);
}
