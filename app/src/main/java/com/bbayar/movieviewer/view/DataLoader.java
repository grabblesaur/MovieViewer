package com.bbayar.movieviewer.view;

import com.bbayar.movieviewer.model.TvResponse;
import com.bbayar.movieviewer.model.rest.ApiClient;
import com.bbayar.movieviewer.model.rest.ApiInterface;

import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Response;

public class DataLoader implements Callable<Response<TvResponse>> {

    @Override
    public Response<TvResponse> call() throws Exception {

        ApiInterface apiService = ApiClient
                .getClient()
                .create(ApiInterface.class);

        Call<TvResponse> call = apiService.getTvResponse(MainActivity.API_KEY);
        return call.execute();
    }
}
