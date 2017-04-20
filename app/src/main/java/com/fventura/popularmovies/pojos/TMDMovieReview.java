package com.fventura.popularmovies.pojos;

/**
 * Created by fventura on 19/04/17.
 */

public class TMDMovieReview {
    private String mReview;
    private String mAuthorName;

    public TMDMovieReview(String authorName, String review) {
        this.mAuthorName = authorName;
        this.mReview = review;
    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public String getmReview() {
        return mReview;
    }
}
