package com.fventura.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by fventura on 14/02/17.
 */

public class TMDMoviePosterAdapter extends ArrayAdapter<TMDMovie> {

    public TMDMoviePosterAdapter(Context context, TMDMovie[] tmdMovieList) {
        super(context, 0, tmdMovieList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new ImageView(getContext());
        }
        final TMDMovie tBDMovie = getItem(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                Class destinationActivity = MovieDetailsActivity.class;
                Intent startMovieDetailsActivity = new Intent(context, destinationActivity);
                startMovieDetailsActivity.putExtra(TMDMovie.class.getName(), tBDMovie);
                context.startActivity(startMovieDetailsActivity);
            }
        });
        Picasso.with(getContext()).load(tBDMovie.getmPosterUri()).into((ImageView) convertView);
        return convertView;
    }

}
