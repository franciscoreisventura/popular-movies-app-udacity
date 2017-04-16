package com.fventura.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by fventura on 16/02/17.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mSynopsis;
    private TextView mDateReleased;
    private TextView mRuntime;
    private TextView mVoteAverage;
    private ImageView mPoster;

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
        if (getIntent().hasExtra("movieid")) {
            int movieId = getIntent().getIntExtra("movieid", 0);
            if(movieId == 0){
                showError();
            }
        }
    }

    private void showError() {
        Toast.makeText(this, R.string.toast_error_no_movie, Toast.LENGTH_SHORT).show();
        finish();
    }
}
