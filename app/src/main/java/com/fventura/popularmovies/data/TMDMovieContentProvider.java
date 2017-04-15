package com.fventura.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by fraven on 15-04-2017.
 */

public class TMDMovieContentProvider extends ContentProvider {

    public static final int TMDMOVIES = 100;
    public static final int TMDMOVIE_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildURIMatcher();
    private TMDMovieDbHelper tmdMovieDbHelper;

    private static UriMatcher buildURIMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(TMDMovieContract.AUTHORITY, TMDMovieContract.PATH_MOVIES, TMDMOVIES);
        uriMatcher.addURI(TMDMovieContract.AUTHORITY, TMDMovieContract.PATH_MOVIES + "/#", TMDMOVIE_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        tmdMovieDbHelper = new TMDMovieDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = tmdMovieDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor resultCursor;

        switch (match) {
            case TMDMOVIES:
                resultCursor = db.query(TMDMovieContract.TMDMovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        resultCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return resultCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = tmdMovieDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case TMDMOVIES:
                long id = db.insert(TMDMovieContract.TMDMovieEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(TMDMovieContract.TMDMovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = tmdMovieDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int deleted;

        switch (match) {
            case TMDMOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                deleted = db.delete(TMDMovieContract.TMDMovieEntry.TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (deleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
