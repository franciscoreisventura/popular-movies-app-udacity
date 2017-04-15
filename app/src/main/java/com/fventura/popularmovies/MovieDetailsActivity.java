package com.fventura.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        if (getIntent().hasExtra(TMDMovie.class.getName())) {
            TMDMovie movie = (TMDMovie) getIntent().getSerializableExtra(TMDMovie.class.getName());
            mTitle.setText(movie.getmTitle());
            mSynopsis.setText(movie.getmSynopsis());
            mDateReleased.setText(movie.getmReleaseDate().split("-")[0]);
            mVoteAverage.setText(movie.getmVoteAverage()+"/10");
            mRuntime.setText(movie.getmRuntime()+"m");
            Picasso.with(this).load(movie.getmBigPosterUri()).into((ImageView) mPoster);
        }
    }
}
