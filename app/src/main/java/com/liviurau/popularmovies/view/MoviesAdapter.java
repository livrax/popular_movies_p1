package com.liviurau.popularmovies.view;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liviurau.popularmovies.R;
import com.liviurau.popularmovies.model.Movie;
import com.liviurau.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.liviurau.popularmovies.view.MovieActivity.SELECTED_MOVIE;

/**
 * Created by Liviu Rau on 17-Feb-18.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private final Context mContext;
    private final List<Movie> movies = new ArrayList<>();

    public MoviesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ConstraintLayout movieLayout;
        private final ImageView posterIV;
        private final TextView titleTV;
        private final TextView releaseTV;

        public MovieViewHolder(View view) {
            super(view);

            movieLayout = view.findViewById(R.id.movie_layout);
            posterIV = view.findViewById(R.id.movie_poster_iv);
            titleTV = view.findViewById(R.id.movie_title_tv);
            releaseTV = view.findViewById(R.id.release_date_tv);

            movieLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int listPosition = Integer.parseInt(posterIV.getTag().toString());

            Movie movie = new Movie();
            movie.setListPosition(listPosition);
            movie.setMovieId(titleTV.getTag().toString());

            Intent i = new Intent(view.getContext(), MovieActivity.class);
            i.putExtra(SELECTED_MOVIE, movie);
            ActivityCompat.startActivity(view.getContext(), i, null);
        }
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_adapter_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Movie movie = movies.get(position);

        holder.titleTV.setText(movie.getTitle());
        holder.titleTV.setTag(movie.getMovieId());

        String releaseYear = movie.getReleaseDate().substring(0,4);
        holder.releaseTV.setText(releaseYear);

        String posterPath = NetworkUtils.POSTER_PATH + movie.getImage();
        Picasso.with(mContext).load(posterPath).into(holder.posterIV);
        holder.posterIV.setTag(position);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void swapList(List<Movie> list){
        if (list != null) {
            this.movies.clear();
            this.movies.addAll(list);
            this.notifyDataSetChanged();
        }
    }
}
