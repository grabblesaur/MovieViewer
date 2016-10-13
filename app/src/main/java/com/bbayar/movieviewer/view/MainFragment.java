package com.bbayar.movieviewer.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bbayar.movieviewer.R;
import com.bbayar.movieviewer.model.Result;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";
    private static String RESULT_LIST_KEY = "result list";

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private Unbinder unbinder;

    private List<Result> resultList;

    public static MainFragment newInstance(List<Result> list) {
        Log.d(TAG, "newInstance() called with: list = [" + list + "]");
        MainFragment fragment = new MainFragment();
        Parcelable p = Parcels.wrap(list);
        Bundle bundle = new Bundle();
        bundle.putParcelable(RESULT_LIST_KEY, p);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        resultList = Parcels.unwrap(getArguments().getParcelable(RESULT_LIST_KEY));
        Log.d(TAG, "onViewCreated: resultList: " + resultList);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() called");
        super.onDestroy();
        unbinder.unbind();
    }
}
