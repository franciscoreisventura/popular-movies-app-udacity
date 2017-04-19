package com.fventura.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fventura.popularmovies.pojos.TMDMovieVideo;

/**
 * Created by fventura on 19/04/17.
 */

public class TMDMovieVideoAdapter extends ArrayAdapter<TMDMovieVideo> {

    private TextView mName;

    public TMDMovieVideoAdapter(Context context, TMDMovieVideo[] tmdMovieVideoList) {
        super(context, 0, tmdMovieVideoList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_tmdmovievideo, null);
        }
        final TMDMovieVideo video = getItem(position);
        mName = (TextView) convertView.findViewById(R.id.tv_video_item_movie_details_name);
        mName.setText(video.getmName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video.getmKey()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + video.getmKey()));
                try {
                    getContext().startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    getContext().startActivity(webIntent);
                }
            }
        });
        return convertView;
    }

}
