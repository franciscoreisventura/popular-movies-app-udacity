package com.fventura.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by fraven on 15-04-2017.
 */

public class TMDMovieContract {

    public static final String AUTHORITY = "com.fventura.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    public static final class TMDMovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();
        public static final String TABLE_NAME = "tmdmovies";
        public static final String COLUMN_TMD_ID = "tmd_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_URI = "poster_uri";
    }
}
