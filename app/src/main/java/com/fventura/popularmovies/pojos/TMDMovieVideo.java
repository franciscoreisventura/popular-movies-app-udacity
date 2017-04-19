package com.fventura.popularmovies.pojos;

/**
 * Created by fventura on 19/04/17.
 */

public class TMDMovieVideo {
    private String mKey;
    private String mName;

    public TMDMovieVideo(String key, String name) {
        this.mKey = key;
        this.mName = name;
    }

    public String getmName() {
        return mName;
    }

    public String getmKey() {
        return mKey;
    }
}
