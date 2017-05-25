package com.tvalerts.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tvalerts.data.ShowsContract.ShowEntry;

/**
 * Created by anita on 10/05/17.
 */

public class ShowDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shows.db";
    private static final int DATABASE_VERSION = 1;

    public ShowDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SHOWS_TABLE = "CREATE TABLE " + ShowEntry.TABLE_NAME + " (" +
                ShowEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ShowEntry.COLUMN_SHOW_API_ID + " INTEGER NOT NULL," +
                ShowEntry.COLUMN_SHOW_NAME + " STRING NOT NULL," +
                ShowEntry.COLUMN_SHOW_URL + " STRING NOT NULL," +
                ShowEntry.COLUMN_SHOW_TYPE + " STRING NOT NULL," +
                ShowEntry.COLUMN_SHOW_LANGUAGE + " STRING NOT NULL," +
                ShowEntry.COLUMN_SHOW_STATUS + " STRING NOT NULL," +
                ShowEntry.COLUMN_SHOW_PREMIERED + " STRING NOT NULL,"
                + " UNIQUE (" + ShowEntry.COLUMN_SHOW_API_ID + ") ON CONFLICT REPLACE);";
        db.execSQL(SQL_CREATE_SHOWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ShowEntry.TABLE_NAME);
        onCreate(db);
    }
}
