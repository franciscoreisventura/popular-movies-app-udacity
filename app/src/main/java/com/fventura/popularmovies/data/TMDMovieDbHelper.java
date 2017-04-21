package com.fventura.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fraven on 15-04-2017.
 */

public class TMDMovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tmdmovies.db";
    private static final int VERSION = 1;


    public TMDMovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + TMDMovieContract.TMDMovieEntry.TABLE_NAME + " (" +
                TMDMovieContract.TMDMovieEntry._ID + " INTEGER PRIMARY KEY, " +
                TMDMovieContract.TMDMovieEntry.COLUMN_TMD_ID + " INTEGER NOT NULL, " +
                TMDMovieContract.TMDMovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                TMDMovieContract.TMDMovieEntry.COLUMN_POSTER_URI + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TMDMovieContract.TMDMovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
