package com.fventura.popularmovies;

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
import com.fventura.popularmovies.pojos.TMDMoviePoster;
import com.fventura.popularmovies.utils.TMDAPIHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private GridView mTMDMoviesGridView;
    private TextView mErrorTextView;
    RequestQueue mQueue;

    //TODO fix application lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTMDMoviesGridView = (GridView) findViewById(R.id.gv_tmdmovies);
        mErrorTextView = (TextView) findViewById(R.id.tv_error);
        showMovies();
        mQueue = Volley.newRequestQueue(this);
        mQueue.add(createJsonRequest(TMDAPIHelper.SORT_OPTIONS.MOST_POPULAR));
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
                //TODO get movies on database
                return true;
        }
        mQueue.add(createJsonRequest(sortOption));
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

    private JsonObjectRequest createJsonRequest(TMDAPIHelper.SORT_OPTIONS sortOption) {
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
}
