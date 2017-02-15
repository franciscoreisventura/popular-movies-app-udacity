package com.fventura.popularmovies.utils;

import android.net.Uri;
import android.os.AsyncTask;

import com.fventura.popularmovies.TMDMovie;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by fventura on 15/02/17.
 */

public class TMDAPIConnector {
    private final static String TMD_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String TMD_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
    private final static String TOP_RATED = "top_rated";
    private final static String API_KEY_QUERY = "api_key";

    public static URL getPopularMoviesURL(String apiKey) {
        Uri uri = Uri.parse(TMD_BASE_URL).buildUpon()
                .appendPath(TOP_RATED).appendQueryParameter(API_KEY_QUERY, apiKey)
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
        return Uri.parse(TMD_IMAGE_URL+poster).buildUpon().build();
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
