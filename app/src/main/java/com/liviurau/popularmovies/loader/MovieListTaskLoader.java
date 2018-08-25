package com.liviurau.popularmovies.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.liviurau.popularmovies.model.Movie;
import com.liviurau.popularmovies.utils.JsonUtils;
import com.liviurau.popularmovies.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Liviu Rau on 21-Feb-18.
 */

public class MovieListTaskLoader extends AsyncTaskLoader<List<Movie>> {

    private final String param;
    private List<Movie> list;

    public MovieListTaskLoader(Context context, String param) {
        super(context);
        this.param = param;
    }

    @Override
    public List<Movie> loadInBackground() {
        URL path = NetworkUtils.buildPath(param);
        try {
            String response = NetworkUtils.getResponse(path);

            list = JsonUtils.parseMovies(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public void deliverResult(List<Movie> data) {
        super.deliverResult(data);
    }
}
