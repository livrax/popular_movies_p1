package com.liviurau.popularmovies.utils;

import android.net.Uri;

import com.liviurau.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Liviu Rau on 18-Feb-18.
 */

public class NetworkUtils {

    private final static String BASE_URL =  "https://api.themoviedb.org/3/movie/";
    private final static String API_KEY = "api_key";

    //TODO Add api key to USER_API_KEY
    private static final String USER_API_KEY = BuildConfig.API_KEY;
    public static final String POSTER_PATH = "http://image.tmdb.org/t/p/w780/";

    public static final String TOP_RATED = "top_rated";
    public static final String POPULAR = "popular";
    public static final String UPCOMING = "upcoming";
    public static final String NOW_PLAYING = "now_playing";

    public static URL buildPath(String query) {
        Uri builtUri = Uri.parse(BASE_URL + query).buildUpon()
                .appendQueryParameter(API_KEY, USER_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponse(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
