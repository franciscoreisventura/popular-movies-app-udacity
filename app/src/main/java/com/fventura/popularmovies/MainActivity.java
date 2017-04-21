package com.fventura.popularmovies;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fventura.popularmovies.data.TMDMovieContract;
import com.fventura.popularmovies.pojos.TMDMoviePoster;
import com.fventura.popularmovies.utils.TMDAPIHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String[] MOVIE_FAVORITES_PROJECTION = {
            TMDMovieContract.TMDMovieEntry._ID,
            TMDMovieContract.TMDMovieEntry.COLUMN_TMD_ID,
            TMDMovieContract.TMDMovieEntry.COLUMN_POSTER_URI
    };
    private static final String TAG = "MainActivity";
    private static final String CURRENT_SORT_PREFERENCE = "current_sort_preference";
    private static final String FAVORITES_SORT = "favorites";
    private String mCurrentSort = TMDAPIHelper.SORT_OPTIONS.MOST_POPULAR.toString();
    private GridView mTMDMoviesGridView;
    private TextView mErrorTextView;
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTMDMoviesGridView = (GridView) findViewById(R.id.gv_tmdmovies);
        mErrorTextView = (TextView) findViewById(R.id.tv_error);
        mQueue = Volley.newRequestQueue(this);
        showMovies();
        if (savedInstanceState != null) {
            mCurrentSort = savedInstanceState.getString(CURRENT_SORT_PREFERENCE);
        }
        else {
            mQueue.add(queryTMDAPI(TMDAPIHelper.SORT_OPTIONS.valueOf(mCurrentSort)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCurrentSort.equals(FAVORITES_SORT)){
            fillGridViewFavorites();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(CURRENT_SORT_PREFERENCE, mCurrentSort);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TMDAPIHelper.SORT_OPTIONS sortOption = null;
        switch (item.getItemId()) {
            case R.id.menu_item_most_popular:
                sortOption = TMDAPIHelper.SORT_OPTIONS.MOST_POPULAR;
                break;
            case R.id.menu_item_top_rated:
                sortOption = TMDAPIHelper.SORT_OPTIONS.TOP_RATED;
                break;
            case R.id.menu_item_favorites:
                fillGridViewFavorites();
                return true;
        }
        mQueue.add(queryTMDAPI(sortOption));
        return true;
    }

    private void showError() {
        mTMDMoviesGridView.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showMovies() {
        mTMDMoviesGridView.setVisibility(View.VISIBLE);
        mErrorTextView.setVisibility(View.INVISIBLE);
    }

    private JsonObjectRequest queryTMDAPI(TMDAPIHelper.SORT_OPTIONS sortOption) {
        mCurrentSort = sortOption.toString();
        return new JsonObjectRequest(TMDAPIHelper.getSortedMoviesURL(getString(R.string.api_key), sortOption).toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error getting movies from TMD", error);
                showError();
            }
        });
    }

    private void parseResponse(JSONObject response) {
        if (response == null) {
            showError();
            return;
        }
        try {
            JSONArray movies = response.getJSONArray("results");
            TMDMoviePoster[] tmdMoviePosters = new TMDMoviePoster[movies.length()];
            for (int i = 0; i < movies.length(); i++) {
                JSONObject currentObject = movies.getJSONObject(i);
                tmdMoviePosters[i] = new TMDMoviePoster();
                tmdMoviePosters[i].setmId(currentObject.getInt("id"));
                tmdMoviePosters[i].setmPosterUri(TMDAPIHelper.getMoviePosterUriString(currentObject.getString("poster_path")));
            }
            TMDMoviePosterAdapter tMDMoviePosterAdapter = new TMDMoviePosterAdapter(MainActivity.this, tmdMoviePosters);
            mTMDMoviesGridView.setAdapter(tMDMoviePosterAdapter);
            tMDMoviePosterAdapter.notifyDataSetChanged();
            showMovies();
        } catch (JSONException e) {
            Log.e(TAG, "Error filling the movies grid", e);
            showError();
        }
    }

    private void fillGridViewFavorites() {
        mCurrentSort = FAVORITES_SORT;
        Cursor movies = getContentResolver().query(TMDMovieContract.TMDMovieEntry.CONTENT_URI, null, null, null, null);
        TMDMoviePoster[] tmdMoviePosters = new TMDMoviePoster[movies.getCount()];
        for (int i = 0; movies.moveToNext(); i++) {
            tmdMoviePosters[i] = new TMDMoviePoster();
            tmdMoviePosters[i].setmId(movies.getInt(movies.getColumnIndex(TMDMovieContract.TMDMovieEntry.COLUMN_TMD_ID)));
            tmdMoviePosters[i].setmPosterUri(TMDAPIHelper.getMoviePosterUriString(movies.getString(movies.getColumnIndex(TMDMovieContract.TMDMovieEntry.COLUMN_POSTER_URI))));
        }
        TMDMoviePosterAdapter tMDMoviePosterAdapter = new TMDMoviePosterAdapter(MainActivity.this, tmdMoviePosters);
        mTMDMoviesGridView.setAdapter(tMDMoviePosterAdapter);
        tMDMoviePosterAdapter.notifyDataSetChanged();
        showMovies();
    }
}
