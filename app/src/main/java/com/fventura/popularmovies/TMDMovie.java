package com.fventura.popularmovies;

import java.util.Date;

/**
 * Created by fventura on 14/02/17.
 */

public class TMDMovie {
    private String mPosterUrl;
    private String mTitle;
    private Date mReleaseDate;
    private double mVoteAverage;
    private String synopsis;

    public TMDMovie() {

    }

    public String getmPosterUrl() {
        return mPosterUrl;
    }

    public void setmPosterUrl(String mPosterUrl) {
        this.mPosterUrl = mPosterUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(Date mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public double getmVoteAverage() {
        return mVoteAverage;
    }

    public void setmVoteAverage(double mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
