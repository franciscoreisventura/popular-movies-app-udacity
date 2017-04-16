package com.fventura.popularmovies.pojos;

import android.net.Uri;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fventura on 14/02/17.
 */

public class TMDMoviePoster {
    private int mId;
    private String mPosterUri;

    public TMDMoviePoster() {

    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmPosterUri() {
        return mPosterUri;
    }

    public void setmPosterUri(String mPosterUri) {
        this.mPosterUri = mPosterUri;
    }
}
