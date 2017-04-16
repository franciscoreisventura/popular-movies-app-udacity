package com.fventura.popularmovies.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by fventura on 15/02/17.
 */

public class TMDAPICallsCreator {
    private final static String TMD_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String TMD_IMAGE_URL = "http://image.tmdb.org/t/p/w500";
    private final static String TMD_BIG_IMAGE_URL = "http://image.tmdb.org/t/p/w780";
    private static final String SORT_MOST_POPULAR = "popular";
    private final static String SORT_TOP_RATED = "top_rated";
    private final static String API_KEY_QUERY = "api_key";
    private final static String TMD_MOVIE_URL = TMD_BASE_URL + "/movie/";

    public enum SORT_OPTIONS {
        MOST_POPULAR,
        TOP_RATED
    }

    public static URL getSortedMoviesURL(String apiKey, SORT_OPTIONS sortBy) {
        String sortByString = SORT_MOST_POPULAR;
        switch (sortBy) {
            case MOST_POPULAR:
                sortByString = SORT_MOST_POPULAR;
                break;
            case TOP_RATED:
                sortByString = SORT_TOP_RATED;
                break;
        }
        Uri uri = Uri.parse(TMD_BASE_URL).buildUpon()
                .appendPath(sortByString).appendQueryParameter(API_KEY_QUERY, apiKey)
                .build();
        URL url = null;
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static String getMoviePosterUriString(String poster) {
        return parse(TMD_IMAGE_URL + poster);
    }

    public static String getBigMoviePosterUriString(String poster) {
        return parse(TMD_BIG_IMAGE_URL + poster);
    }

    public static String getMovieById(int id) {
        return parse(TMD_MOVIE_URL + id);
    }

    private static String parse(String uri) {
        return Uri.parse(uri).buildUpon().build().toString();
    }
}
