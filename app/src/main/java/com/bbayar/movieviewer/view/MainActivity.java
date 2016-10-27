package com.bbayar.movieviewer.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bbayar.movieviewer.R;
import com.bbayar.movieviewer.model.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static String GLOBAL_TAG = "global";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<Result> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(GLOBAL_TAG, "MainActivity onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null || resultList == null) {
            resultList = new ArrayList<>();
        }

        loadData();
        showMainFragment();
    }

    private void loadData() {
        LoaderManager.LoaderCallbacks<List<Result>> callbacks =
                new ResultLoaderCallback(this);
        getSupportLoaderManager().initLoader(R.id.result_loader,
                Bundle.EMPTY,
                callbacks);
    }

    private void showMainFragment() {
        Log.i(GLOBAL_TAG, "MainActivity showMainFragment() called");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        MainFragment.newInstance(resultList),
                        MainFragment.class.getSimpleName())
                .commit();
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

    public class ResultLoaderCallback implements LoaderManager.LoaderCallbacks<List<Result>> {

        private Context context;

        public ResultLoaderCallback(Context context) {
            this.context = context;
        }

        @Override
        public Loader<List<Result>> onCreateLoader(int id, Bundle args) {
            return new ResultLoader(context);
        }

        @Override
        public void onLoadFinished(Loader<List<Result>> loader, List<Result> data) {
            resultList.addAll(data);
            MainFragment fragment = (MainFragment) getSupportFragmentManager()
                    .findFragmentByTag(MainFragment.class.getSimpleName());
            fragment.getMoviesAdapter().notifyItemInserted(0);
            fragment.getMoviesAdapter().notifyItemRangeChanged(0, resultList.size());
        }

        @Override
        public void onLoaderReset(Loader<List<Result>> loader) {

        }
    }
}
