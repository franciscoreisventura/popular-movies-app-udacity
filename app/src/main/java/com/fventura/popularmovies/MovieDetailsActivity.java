package com.fventura.popularmovies;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by fventura on 16/02/17.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        mTitle = (TextView) findViewById(R.id.tv_movie_details_value);
        if (getIntent().hasExtra(TMDMovie.class.getName())) {
            TMDMovie movie = (TMDMovie) getIntent().getSerializableExtra(TMDMovie.class.getName());
            mTitle.setText(movie.getmTitle());
        }
    }
}
