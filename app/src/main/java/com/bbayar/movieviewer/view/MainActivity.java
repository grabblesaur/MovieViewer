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

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static String API_KEY = "21f91637045fc30ac59759b75acc9ca0";

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
            try {
                resultList = loadData();
            } catch (ExecutionException e) {
                Log.d(TAG, "ExecutionException");
            } catch (InterruptedException e) {
                Log.d(TAG, "InterruptedException");
            }
        }

        Log.d(TAG, "resultList = " + resultList);

        //showMainFragment();

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

    private List<Result> loadData() throws ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Future<Response<TvResponse>> future = exec.submit(new DataLoader());
        List<Result> results = future.get().body().getResults();
        exec.shutdown();
        return results;
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
