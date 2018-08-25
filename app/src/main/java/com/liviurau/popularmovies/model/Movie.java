package com.liviurau.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

/**
 * Created by Liviu Rau on 17-Feb-18.
 */

public class Movie implements Parcelable {

    private int listPosition;
    private String movieId;
    private String title;
    private String tagline;
    private String image;
    private String overview;
    private String releaseDate;
    private String runtime;
    private String budget;
    private String revenue;
    private Map<Integer, String> genres;
    private Map<Integer, String> productionCompanies;

    public Movie() {
    }

    private Movie(Parcel in) {
        listPosition = in.readInt();
        movieId = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getListPosition() {
        return listPosition;
    }

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public Map<Integer, String> getGenres() {
        return genres;
    }

    public void setGenres(Map<Integer, String> genres) {
        this.genres = genres;
    }

    public Map<Integer, String> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(Map<Integer, String> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(listPosition);
        parcel.writeString(movieId);
    }
}
