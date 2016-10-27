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
import com.bbayar.movieviewer.view.adapter.MoviesAdapter;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.bbayar.movieviewer.view.MainActivity.GLOBAL_TAG;

public class MainFragment extends Fragment {

    private static final String RESULT_LIST = "result list";
    public static String API_KEY = "21f91637045fc30ac59759b75acc9ca0";

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private Unbinder unbinder;

    private List<Result> resultList;
    private MoviesAdapter moviesAdapter;

    public static MainFragment newInstance(List<Result> resultList) {
        Log.i(GLOBAL_TAG, "MainFragment newInstance(): ");
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RESULT_LIST, Parcels.wrap(resultList));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(GLOBAL_TAG, "MainFragment onCreateView():");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(GLOBAL_TAG, "MainFragment onViewCreated():");
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        resultList = Parcels.unwrap(getArguments().getParcelable(RESULT_LIST));
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Log.i(GLOBAL_TAG, "MainFragment setUpRecyclerView: ");
        moviesAdapter = new MoviesAdapter(resultList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(moviesAdapter);
    }

    @Override
    public void onDestroy() {
        Log.i(GLOBAL_TAG, "MainFragment onDestroy() called");
        super.onDestroy();
        unbinder.unbind();
    }

    public MoviesAdapter getMoviesAdapter() {
        return moviesAdapter;
    }
}
