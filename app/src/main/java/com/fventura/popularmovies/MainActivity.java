package com.fventura.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        new MoviePosterFiller(this).execute(TMDAPIConnector.getPopularMoviesURL(getString(R.string.api_key)));
    }

    private void fillMovieList() {
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
            //TODO CHECK IF OBJECT IS NULL AND PUT EMPTY IMAGE
            try {
                JSONObject resultJSON = new JSONObject(result);
                JSONArray movies = resultJSON.getJSONArray("results");
                Uri[] moviePosterUris = new Uri[movies.length()];
                for (int i = 0; i < movies.length(); i++) {
                    moviePosterUris[i] = TMDAPIConnector.getMoviePosterUri(movies.getJSONObject(i).getString("poster_path"));
                }
                TMDMoviePosterAdapter tMDMoviePosterAdapter = new TMDMoviePosterAdapter(mContext, moviePosterUris);
                mTMDMoviesGridView.setAdapter(tMDMoviePosterAdapter);
                tMDMoviePosterAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace(); //TODO EMPTY IMAGE TOO
            }
        }
    }
}
