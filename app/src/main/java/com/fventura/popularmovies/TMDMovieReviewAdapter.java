package com.fventura.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fventura.popularmovies.pojos.TMDMovieReview;

/**
 * Created by fventura on 19/04/17.
 */

public class TMDMovieReviewAdapter extends ArrayAdapter<TMDMovieReview> {

    private TextView mAuthorName;
    private TextView mReview;

    public TMDMovieReviewAdapter(Context context, TMDMovieReview[] tmdMovieReviewList) {
        super(context, 0, tmdMovieReviewList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_tmdmoviereview, null);
        }
        final TMDMovieReview review = getItem(position);
        mAuthorName = (TextView) convertView.findViewById(R.id.tv_movie_details_review_author_name);
        mAuthorName.setText(review.getmAuthorName());
        mReview = (TextView) convertView.findViewById(R.id.tv_movie_details_review_review);
        mReview.setText(review.getmReview());
        return convertView;
    }

}
