package com.liviurau.popularmovies.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.liviurau.popularmovies.R;
import com.liviurau.popularmovies.loader.MovieListTaskLoader;
import com.liviurau.popularmovies.model.Movie;
import com.liviurau.popularmovies.utils.NetworkUtils;

import java.util.List;

/**
 * Created by Liviu Rau on 18-Feb-18.
 */

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final Integer POPULAR_MOVIE_TASK = 45;
    private static final Integer UPCOMING_MOVIE_TASK = 56;
    private static final Integer TOP_RATED_MOVIE_TASK = 67;
    private static final Integer NOW_PLAYING_MOVIE_TASK = 78;

    private static final Integer LIST_COLUMNS = 2;

    private boolean connected = false;

    private RecyclerView movieListRV;
    private MoviesAdapter moviesAdapter;
    private ProgressBar mLoadingIndicator;
    private LoaderManager mLoaderManager;
    private String movieListType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("");
        mLoaderManager = getLoaderManager();
        movieListType = NetworkUtils.POPULAR;

        movieListRV = findViewById(R.id.movie_list_rv);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        moviesAdapter = new MoviesAdapter(getApplicationContext());

        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(LIST_COLUMNS, StaggeredGridLayoutManager.VERTICAL);

        movieListRV.setLayoutManager(mStaggeredLayoutManager);
        movieListRV.setAdapter(moviesAdapter);

        if (isInternetConnection()) {
            showLoading();
            startMovieTask(POPULAR_MOVIE_TASK);
        } else {
            Toast.makeText(getApplicationContext(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
        }
    }

    private boolean isInternetConnection() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null &&
                    networkInfo.isAvailable() &&
                    networkInfo.isConnected();

            return connected;

        } catch (Exception e) {
            Log.v("NetworkConnection", e.toString());
        }
        return connected;
    }

    private void showDataView() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        movieListRV.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        movieListRV.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void startMovieTask(int taskId) {
        if (isInternetConnection()) {
            mLoaderManager.initLoader(taskId, null, this);
        } else {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        return new MovieListTaskLoader(this, movieListType);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<Movie>> loader, List<Movie> movies) {
        moviesAdapter.swapList(movies);
        showDataView();
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<Movie>> loader) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.top_rated:
                movieListType = NetworkUtils.TOP_RATED;
                showLoading();
                startMovieTask(TOP_RATED_MOVIE_TASK);
                break;
            case R.id.upcoming:
                movieListType = NetworkUtils.UPCOMING;
                showLoading();
                startMovieTask(UPCOMING_MOVIE_TASK);
                break;
            case R.id.now_playing:
                movieListType = NetworkUtils.NOW_PLAYING;
                showLoading();
                startMovieTask(NOW_PLAYING_MOVIE_TASK);
                break;
            default:
                movieListType = NetworkUtils.POPULAR;
                showLoading();
                startMovieTask(POPULAR_MOVIE_TASK);
                break;
        }
        return true;
    }

}
