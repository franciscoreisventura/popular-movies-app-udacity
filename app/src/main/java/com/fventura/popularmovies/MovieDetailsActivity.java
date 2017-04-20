package com.fventura.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fventura.popularmovies.pojos.TMDMovieReview;
import com.fventura.popularmovies.pojos.TMDMovieVideo;
import com.fventura.popularmovies.utils.TMDAPIHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fventura on 16/02/17.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    private static String TAG = "MovieDetailsActivity";
    private TextView mTitle;
    private TextView mSynopsis;
    private TextView mDateReleased;
    private TextView mRuntime;
    private TextView mVoteAverage;
    private ImageView mPoster;
    private ListView mVideosListView;
    private ListView mReviewsListView;

    private RequestQueue mQueue;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        mPoster = (ImageView) findViewById(R.id.iv_movie_details_poster);
        mTitle = (TextView) findViewById(R.id.tv_movie_details_title);
        mSynopsis = (TextView) findViewById(R.id.tv_movie_details_synopsis);
        mDateReleased = (TextView) findViewById(R.id.tv_movie_details_date_release);
        mRuntime = (TextView) findViewById(R.id.tv_movie_details_runtime);
        mVoteAverage = (TextView) findViewById(R.id.tv_movie_details_vote_average);
        mVideosListView = (ListView) findViewById(R.id.lv_movie_details_videos);
        mReviewsListView = (ListView) findViewById(R.id.lv_movie_details_reviews);
        mQueue = Volley.newRequestQueue(this);

        if (getIntent().hasExtra("movieid")) {
            int movieId = getIntent().getIntExtra("movieid", 0);
            if(movieId == 0){
                Log.e(TAG, "No movie id provided to activity");
                showError();
            }
            mQueue.add(fillMovieDetails(movieId));
            mQueue.add(fillMovieVideos(movieId));
            mQueue.add(fillMovieReviews(movieId));
        }
    }

    private JsonObjectRequest fillMovieDetails(final int movieId) {
        return new JsonObjectRequest(TMDAPIHelper.getMovieById(getString(R.string.api_key), movieId).toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mTitle.setText(response.getString("title"));
                            mSynopsis.setText(response.getString("overview"));
                            mDateReleased.setText(response.getString("release_date").substring(0,4));
                            mRuntime.setText(response.getInt("runtime")+"m");
                            mVoteAverage.setText(response.getDouble("vote_average")+"/10");
                            Picasso.with(MovieDetailsActivity.this).load(TMDAPIHelper.getBigMoviePosterUriString(response.getString("poster_path"))).into(mPoster);
                        }catch (JSONException e){
                            Log.e(TAG, "Error parsing JSON for movie: " + movieId, e);
                            showError();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error getting movie details from TMD for movie: " + movieId, error);
                showError();
            }
        });
    }

    private JsonObjectRequest fillMovieVideos(final int movieId) {
        return new JsonObjectRequest(TMDAPIHelper.getVideosFromMovie(getString(R.string.api_key), movieId).toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray videos = response.getJSONArray("results");
                            TMDMovieVideo[] tmdMovieVideos = new TMDMovieVideo[videos.length()];
                            for (int i = 0; i < videos.length(); i++) {
                                JSONObject currentObject = videos.getJSONObject(i);
                                tmdMovieVideos[i] = new TMDMovieVideo(currentObject.getString("key"), currentObject.getString("name"));
                            }
                            TMDMovieVideoAdapter tmdMovieVideoAdapter = new TMDMovieVideoAdapter(MovieDetailsActivity.this, tmdMovieVideos);
                            mVideosListView.setAdapter(tmdMovieVideoAdapter);
                            tmdMovieVideoAdapter.notifyDataSetChanged();
                        }catch (JSONException e){
                            Log.e(TAG, "Error parsing videos JSON for movie: " + movieId, e);
                            showError();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error getting movie videos from TMD for movie: " + movieId, error);
                showError();
            }
        });
    }

    private JsonObjectRequest fillMovieReviews(final int movieId) {
        return new JsonObjectRequest(TMDAPIHelper.getReviewsFromMovie(getString(R.string.api_key), movieId).toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray reviews = response.getJSONArray("results");
                            TMDMovieReview[] tmdMovieReviews = new TMDMovieReview[reviews.length()];
                            for (int i = 0; i < reviews.length(); i++) {
                                JSONObject currentObject = reviews.getJSONObject(i);
                                tmdMovieReviews[i] = new TMDMovieReview(currentObject.getString("author"), currentObject.getString("content"));
                            }
                            TMDMovieReviewAdapter tmdMovieReviewAdapter = new TMDMovieReviewAdapter(MovieDetailsActivity.this, tmdMovieReviews);
                            mReviewsListView.setAdapter(tmdMovieReviewAdapter);
                            tmdMovieReviewAdapter.notifyDataSetChanged();
                        }catch (JSONException e){
                            Log.e(TAG, "Error parsing reviews JSON for movie: " + movieId, e);
                            showError();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error getting movie reviews from TMD for movie: " + movieId, error);
                showError();
            }
        });
    }

    private void showError() {
        Toast.makeText(this, R.string.toast_error_no_movie, Toast.LENGTH_SHORT).show();
        finish();
    }
}
