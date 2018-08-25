package com.liviurau.popularmovies.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.liviurau.popularmovies.model.Movie;
import com.liviurau.popularmovies.utils.JsonUtils;
import com.liviurau.popularmovies.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Liviu Rau on 21-Feb-18.
 */

public class MovieTaskLoader extends AsyncTaskLoader<Movie> {

    private final String param;
    private Movie movie;

    public MovieTaskLoader(Context context, String param) {
        super(context);
        this.param = param;
    }

    @Override
    public Movie loadInBackground() {
        URL path = NetworkUtils.buildPath(param);
        try {
            String response = NetworkUtils.getResponse(path);

            movie = JsonUtils.parseMovie(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movie;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public void deliverResult(Movie data) {
        super.deliverResult(data);
    }
}
