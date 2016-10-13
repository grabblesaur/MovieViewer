package com.bbayar.movieviewer.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bbayar.movieviewer.R;
import com.bbayar.movieviewer.model.Result;
import com.bbayar.movieviewer.model.TvResponse;
import com.bbayar.movieviewer.model.rest.ApiClient;
import com.bbayar.movieviewer.model.rest.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static String API_KEY = "21f91637045fc30ac59759b75acc9ca0";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<Result> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null || resultList == null) {
            loadData();
        }
        showMainFragment();

    }

    private void showMainFragment() {
        Log.d(TAG, "showMainFragment() called");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,
                        MainFragment.newInstance(resultList),
                        MainFragment.class.getSimpleName())
                .commit();
    }

    private synchronized void loadData() {
        Log.d(TAG, "loadData() called");
        ApiInterface apiService = ApiClient
                .getClient()
                .create(ApiInterface.class);
        Call<TvResponse> call = apiService.getTvResponse(API_KEY);
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                resultList = response.body().getResults();
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    @OnClick(R.id.fab) public void onFabClick(View view) {
        Snackbar.make(view, "Replace with your action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
