package com.fventura.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.fventura.popularmovies.pojos.TMDMoviePoster;
import com.squareup.picasso.Picasso;

/**
 * Created by fventura on 14/02/17.
 */

public class TMDMoviePosterAdapter extends ArrayAdapter<TMDMoviePoster> {

    public TMDMoviePosterAdapter(Context context, TMDMoviePoster[] tmdMoviePosterList) {
        super(context, 0, tmdMoviePosterList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new ImageView(getContext());
        }
        final TMDMoviePoster tBDMovie = getItem(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                Intent startMovieDetailsActivity = new Intent(context, MovieDetailsActivity.class);
                startMovieDetailsActivity.putExtra("movieid", tBDMovie.getmId());
                context.startActivity(startMovieDetailsActivity);
            }
        });
        Picasso.with(getContext()).load(tBDMovie.getmPosterUri()).into((ImageView) convertView);
        return convertView;
    }

}
