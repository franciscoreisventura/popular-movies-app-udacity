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

public class TMDAPIConnector {
    private final static String TMD_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String TMD_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
    private static final String SORT_MOST_POPULAR = "popular";
    private final static String SORT_TOP_RATED = "top_rated";
    private final static String API_KEY_QUERY = "api_key";

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
            e.printStackTrace(); //TODO ERROR HANDLING
            return null;
        }
    }

    public static Uri getMoviePosterUri(String poster) {
        return Uri.parse(TMD_IMAGE_URL + poster).buildUpon().build();
    }

    public static String getResponseFromAPI(URL... params) throws IOException {
        if (params[0] == null || params[0].equals("")) {
            return null;
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) params[0].openConnection();
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
