package com.fventura.popularmovies;

import android.net.Uri;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fventura on 14/02/17.
 */

public class TMDMovie implements Serializable {
    private String mPosterUri;
    private String mBigPosterUri;
    private String mTitle;
    private String mReleaseDate;
    private int mRuntime;
    private double mVoteAverage;
    private String mSynopsis;

    public TMDMovie() {

    }

    public String getmPosterUri() {
        return mPosterUri;
    }

    public void setmPosterUri(String mPosterUri) {
        this.mPosterUri = mPosterUri;
    }

    public String getmBigPosterUri() {
        return mBigPosterUri;
    }

    public void setmBigPosterUri(String mBigPosterUri) {
        this.mBigPosterUri = mBigPosterUri;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public int getmRuntime() {
        return mRuntime;
    }

    public void setmRuntime(int mRuntime) {
        this.mRuntime = mRuntime;
    }

    public double getmVoteAverage() {
        return mVoteAverage;
    }

    public void setmVoteAverage(double mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public String getmSynopsis() {
        return mSynopsis;
    }

    public void setmSynopsis(String mSynopsis) {
        this.mSynopsis = mSynopsis;
    }
}
