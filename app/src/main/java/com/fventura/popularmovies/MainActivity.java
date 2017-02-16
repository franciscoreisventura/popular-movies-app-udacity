package com.fventura.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.fventura.popularmovies.utils.TMDAPIConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private GridView mTMDMoviesGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTMDMoviesGridView = (GridView) findViewById(R.id.gv_tmdmovies);
        new MoviePosterFiller(this).execute(TMDAPIConnector.getSortedMoviesURL(getString(R.string.api_key), TMDAPIConnector.SORT_OPTIONS.MOST_POPULAR));
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
        new MoviePosterFiller(this).execute(TMDAPIConnector.getSortedMoviesURL(getString(R.string.api_key), sortOption));
        return true;
    }

    public class MoviePosterFiller extends AsyncTask<URL, Void, String> {

        private Context mContext;

        MoviePosterFiller(Context context) {
            this.mContext = context;
        }

        @Override
        protected String doInBackground(URL... params) {
            try {
                if (params[0] != null) {
                    return TMDAPIConnector.getResponseFromAPI(params[0]);
                }
                return null;
            } catch (IOException e) { //TODO ERROR HANDLING
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                return;
            }
            try {
                JSONObject resultJSON = new JSONObject(result);
                JSONArray movies = resultJSON.getJSONArray("results");
                TMDMovie[] tmdMovies = new TMDMovie[movies.length()];
                for (int i = 0; i < movies.length(); i++) {
                    tmdMovies[i] = new TMDMovie();
                    tmdMovies[i].setmTitle(movies.getJSONObject(i).getString("title"));
                    tmdMovies[i].setmReleaseDate(movies.getJSONObject(i).getString(("release_date")));
                    tmdMovies[i].setmSynopsis(movies.getJSONObject(i).getString("overview"));
                    tmdMovies[i].setmVoteAverage(Double.parseDouble(movies.getJSONObject(i).getString("vote_average")));
                    tmdMovies[i].setmPosterUri(TMDAPIConnector.getMoviePosterUriString(movies.getJSONObject(i).getString("poster_path")));
                }
                TMDMoviePosterAdapter tMDMoviePosterAdapter = new TMDMoviePosterAdapter(mContext, tmdMovies);
                mTMDMoviesGridView.setAdapter(tMDMoviePosterAdapter);
                tMDMoviePosterAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace(); //TODO EMPTY IMAGE TOO
            }
        }
    }
}
