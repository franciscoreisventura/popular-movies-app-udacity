package com.fventura.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fventura on 14/02/17.
 */

public class TMDMoviePosterAdapter extends ArrayAdapter<Uri> {

    public TMDMoviePosterAdapter(Context context, Uri[] tmdMovieList) {
        //todo verifynulls?
        super(context, 0, tmdMovieList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new ImageView(getContext());
        }
        Uri tBDMovieUri = getItem(position);
        Picasso.with(getContext()).load(tBDMovieUri).into((ImageView) convertView);
        return convertView;
    }
}
