package com.liviurau.popularmovies.view;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liviurau.popularmovies.R;
import com.liviurau.popularmovies.loader.MovieTaskLoader;
import com.liviurau.popularmovies.model.Movie;
import com.liviurau.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.Map;

/**
 * Created by Liviu Rau on 18-Feb-18.
 */

public class MovieActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie> {

    public static final String SELECTED_MOVIE = "selected_movie";
    private static final int DEFAULT_POSITION = -1;

    private String movieId;

    private ImageView movieIV;
    private TextView originalTitleTV;
    private TextView taglineTV;
    private TextView genresTV;
    private TextView overviewTV;
    private TextView productionTV;
    private TextView releaseDateTV;
    private TextView budgetTV;
    private TextView revenueTV;
    private TextView runtimeTV;

    private LoaderManager mLoaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mLoaderManager = getLoaderManager();

        movieIV = findViewById(R.id.poster_iv);
        originalTitleTV = findViewById(R.id.movie_title_tv);
        taglineTV = findViewById(R.id.tagline_tv);
        genresTV = findViewById(R.id.genres_tv);
        overviewTV = findViewById(R.id.overview_tv);
        productionTV = findViewById(R.id.production_tv);
        releaseDateTV = findViewById(R.id.release_date_tv);
        budgetTV = findViewById(R.id.budget_tv);
        revenueTV = findViewById(R.id.revenue_tv);
        runtimeTV = findViewById(R.id.runtime_tv);

        Movie movie = getIntent().getParcelableExtra(SELECTED_MOVIE);
        if (movie.getListPosition() == DEFAULT_POSITION){
            closeOnError();
            return;
        } else {
            movieId = movie.getMovieId();
            startMovieTask();
        }


        if (mLoaderManager.getLoader(1) != null){
            startMovieTask();
        }

    }

    private void startMovieTask() {
        mLoaderManager.initLoader(1, null, this);
    }

    @Override
    public Loader<Movie> onCreateLoader(int i, Bundle bundle) {
        return new MovieTaskLoader(this, movieId);
    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, Movie movie) {
        populateUI(movie);
    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {

    }

    private void populateUI(Movie selectedMovie) {
        setTitle(selectedMovie.getTitle());
        String image = selectedMovie.getImage();
        String posterPath = NetworkUtils.POSTER_PATH + image;
        Picasso.with(getApplicationContext()).load(posterPath).into(movieIV);

        String title = selectedMovie.getTitle();
        originalTitleTV.setText(title);

        String tagline = selectedMovie.getTagline();
        if (!tagline.isEmpty()) {
            taglineTV.setText(tagline);
        } else {
            taglineTV.setVisibility(View.GONE);
        }

        Map<Integer, String> genres = selectedMovie.getGenres();
        StringBuilder genresSB = new StringBuilder();
        for (Map.Entry m: genres.entrySet()){
            genresSB.append(m.getValue()).append("  ");
        }
        genresTV.setText(genresSB);

        String overview = selectedMovie.getOverview();
        overviewTV.setText(overview);

        Map<Integer, String> production = selectedMovie.getProductionCompanies();
        StringBuilder productionCompaniesSB = new StringBuilder();
        productionCompaniesSB.append("Production: \n");
        for (Map.Entry m: production.entrySet()){
            productionCompaniesSB.append(m.getValue()).append("\n");
        }
        productionTV.setText(productionCompaniesSB);

        String releaseDate = selectedMovie.getReleaseDate();
        if (!releaseDate.isEmpty()) {
            releaseDateTV.setText(String.format("Release date: %s", releaseDate));
        } else {
            releaseDateTV.setVisibility(View.GONE);
        }

        String budget = selectedMovie.getBudget();
        if (!budget.isEmpty()) {
            budgetTV.setText(String.format("Budget: $ %s", budget));
        } else {
            budgetTV.setVisibility(View.GONE);
        }

        String revenue = selectedMovie.getRevenue();
        if (!revenue.isEmpty()){
            revenueTV.setText(String.format("Revenue: $ %s", revenue));
        } else {
            revenueTV.setVisibility(View.GONE);
        }

        String runtime = selectedMovie.getRuntime();
        if (!runtime.isEmpty()){
            runtimeTV.setText(String.format("%s min", runtime));
        } else {
            runtimeTV.setVisibility(View.GONE);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.movie_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
