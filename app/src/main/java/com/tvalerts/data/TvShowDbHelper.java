package com.tvalerts.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tvalerts.data.TvShowContract.*;

/**
 * Class that holds the database helper.
 * This class handle the operations to create or update the database.
 */

public class TvShowDbHelper extends SQLiteOpenHelper {

    /**
     * Constant that holds the name of the SQLite database for the Tv shows.
     */
    private static final String DATABASE_NAME = "shows.db";
    /**
     * Constant that holds the current database version number.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructor of the class. It only calls it parents to construct the instance.
     * @param context The context of this class. Usually the calling activity.
     */
    public TvShowDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Method call the first thime the 'shows.db' is created.
     * It defines the table structure reading the values from the contract.
     * @param sqLiteDatabase The instance of the database that will be created.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_SHOWS_TABLE = "CREATE TABLE " +
                TvShowEntry.TABLE_NAME + " (" +
                TvShowEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TvShowEntry.COLUMN_SHOW_ID + " TEXT NOT NULL, " +
                TvShowEntry.COLUMN_SHOW_NAME + " TEXT NOT NULL, " +
                TvShowEntry.COLUMN_SHOW_URL + " TEXT NOT NULL, " +
                TvShowEntry.COLUMN_SHOW_IMAGE + " TEXT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_SHOWS_TABLE);
    }

    /**
     * Method that gets executed when the 'shows.db' database is upgrade.
     * As of right now it will only drop the current database an generate a new one.
     * @param sqLiteDatabase The database instance that will be upgraded.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TvShowEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
