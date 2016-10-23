package com.bbayar.movieviewer.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bbayar.movieviewer.R;
import com.bbayar.movieviewer.model.Result;
import com.bbayar.movieviewer.model.TvResponse;
import com.bbayar.movieviewer.model.rest.ApiClient;
import com.bbayar.movieviewer.model.rest.ApiInterface;
import com.bbayar.movieviewer.view.adapter.MoviesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bbayar.movieviewer.view.MainActivity.GLOBAL_TAG;

public class MainFragment extends Fragment {

    private static String API_KEY = "21f91637045fc30ac59759b75acc9ca0";

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private Unbinder unbinder;

    private List<Result> resultList;
    private MoviesAdapter moviesAdapter;

    public static MainFragment newInstance() {
        Log.d(GLOBAL_TAG, "MainFragment newInstance(): ");
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(GLOBAL_TAG, "MainFragment onCreate: ");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(GLOBAL_TAG, "MainFragment onCreateView():");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(GLOBAL_TAG, "MainFragment onViewCreated():");
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        if (resultList == null) {
            resultList = new ArrayList<>();
            loadData();
        }
        setUpRecyclerView();
    }

    private void loadData() {
        Log.d(GLOBAL_TAG, "MainFragment loadData: started");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TvResponse> call = apiService.getTvResponse(API_KEY);
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                resultList.addAll(response.body().getResults());
                Log.d(GLOBAL_TAG, "MainFragment onResponse: resultList = " + resultList);

                //// TODO: 19.10.2016 something with notify, that some items are added
                moviesAdapter.notifyItemInserted(0);
                moviesAdapter.notifyItemRangeChanged(0, resultList.size());
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                Log.d(GLOBAL_TAG, "MainFragment onFailure: " + t.toString());
                t.printStackTrace();
            }
        });
        Log.d(GLOBAL_TAG, "MainFragment loadData: ended");
    }

    private void setUpRecyclerView() {
        Log.d(GLOBAL_TAG, "MainFragment setUpRecyclerView: ");
        moviesAdapter = new MoviesAdapter(resultList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(moviesAdapter);
    }

    @Override
    public void onDestroy() {
        Log.d(GLOBAL_TAG, "MainFragment onDestroy() called");
        super.onDestroy();
        unbinder.unbind();
    }
}
