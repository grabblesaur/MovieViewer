package com.bbayar.movieviewer.model.rest;

// https://api.themoviedb.org/3/tv/on_the_air?api_key=21f91637045fc30ac59759b75acc9ca0

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL =
            "https://api.themoviedb.org/3/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
