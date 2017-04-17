package com.fventura.popularmovies.pojos;

/**
 * Created by fventura on 16/04/17.
 */

//TODO this might end up not being necessary
public class TMDMovie {

    private int mId;
    private String mBigPosterUri;
    private int mRuntime;
    private int mYear;
    private String mTitle;
    private String mDescription;
    private double mRating;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmBigPosterUri() {
        return mBigPosterUri;
    }

    public void setmBigPosterUri(String mBigPosterUri) {
        this.mBigPosterUri = mBigPosterUri;
    }

    public int getmRuntime() {
        return mRuntime;
    }

    public void setmRuntime(int mRuntime) {
        this.mRuntime = mRuntime;
    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public double getmRating() {
        return mRating;
    }

    public void setmRating(double mRating) {
        this.mRating = mRating;
    }
}
