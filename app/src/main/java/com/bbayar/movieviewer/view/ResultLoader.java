package com.bbayar.movieviewer.view;

import android.content.Context;
import android.util.Log;

import com.bbayar.movieviewer.model.Result;
import com.bbayar.movieviewer.model.rest.ApiClient;
import com.bbayar.movieviewer.model.rest.ApiInterface;

import java.io.IOException;
import java.util.List;

public class ResultLoader
        extends android.support.v4.content.AsyncTaskLoader<List<Result>> {

    public ResultLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Result> loadInBackground() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        try {
            return apiService.getTvResponse(MainFragment.API_KEY).execute().body().getResults();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(MainActivity.GLOBAL_TAG, "loadInBackground: ended with IOException");
        }
        return null;
    }
}
