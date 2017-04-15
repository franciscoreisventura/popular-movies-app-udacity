package com.fventura.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fventura.popularmovies.utils.TMDAPIConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private GridView mTMDMoviesGridView;
    private TextView mErrorTextView;
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTMDMoviesGridView = (GridView) findViewById(R.id.gv_tmdmovies);
        mErrorTextView = (TextView) findViewById(R.id.tv_error);
        showMovies();
        mQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = createJsonRequest(TMDAPIConnector.SORT_OPTIONS.MOST_POPULAR);
        mQueue.add(jsonRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TMDAPIConnector.SORT_OPTIONS sortOption = null;
        switch (item.getItemId()) {
            case R.id.menu_item_most_popular:
                sortOption = TMDAPIConnector.SORT_OPTIONS.MOST_POPULAR;
                break;
            case R.id.menu_item_top_rated:
                sortOption = TMDAPIConnector.SORT_OPTIONS.TOP_RATED;
                break;
        }
        JsonObjectRequest jsonRequest = createJsonRequest(sortOption);
        mQueue.add(jsonRequest);
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

    private JsonObjectRequest createJsonRequest(TMDAPIConnector.SORT_OPTIONS sortOption) {
        return new JsonObjectRequest(TMDAPIConnector.getSortedMoviesURL(getString(R.string.api_key), sortOption).toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
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
            TMDMovie[] tmdMovies = new TMDMovie[movies.length()];
            for (int i = 0; i < movies.length(); i++) {
                tmdMovies[i] = new TMDMovie();
                tmdMovies[i].setmTitle(movies.getJSONObject(i).getString("title"));
                tmdMovies[i].setmReleaseDate(movies.getJSONObject(i).getString(("release_date")));
                tmdMovies[i].setmSynopsis(movies.getJSONObject(i).getString("overview"));
                tmdMovies[i].setmVoteAverage(Double.parseDouble(movies.getJSONObject(i).getString("vote_average")));
                tmdMovies[i].setmPosterUri(TMDAPIConnector.getMoviePosterUriString(movies.getJSONObject(i).getString("poster_path")));
                tmdMovies[i].setmBigPosterUri(TMDAPIConnector.getBigMoviePosterUriString(movies.getJSONObject(i).getString("poster_path")));
            }
            TMDMoviePosterAdapter tMDMoviePosterAdapter = new TMDMoviePosterAdapter(MainActivity.this, tmdMovies);
            mTMDMoviesGridView.setAdapter(tMDMoviePosterAdapter);
            tMDMoviePosterAdapter.notifyDataSetChanged();
            showMovies();
        } catch (JSONException e) {
            showError();
        }
    }
}
