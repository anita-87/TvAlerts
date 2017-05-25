package com.tvalerts.data.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tvalerts.data.ShowDbHelper;
import com.tvalerts.data.ShowsContract;

/**
 * Created by anita on 18/05/17.
 */

public class ShowProvider extends ContentProvider {

    private static final int CODE_SHOWS = 100;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private ShowDbHelper mShowHelper;

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ShowsContract.CONTENT_AUTHORITY;

        // This URI is content://com.tvalerts/shows/
        matcher.addURI(authority, ShowsContract.PATH_SHOWS, CODE_SHOWS);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        // Instantiate the mShowHelpter
        mShowHelper = new ShowDbHelper(getContext());
        // Return true to from onCreate to signify success performing the operation
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mShowHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case CODE_SHOWS:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long id = db.insert(
                                ShowsContract.ShowEntry.TABLE_NAME,
                                null,
                                value
                        );
                        if (id != -1)
                            rowsInserted++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)) {

            case CODE_SHOWS: {
                cursor = mShowHelper.getReadableDatabase().query(
                        ShowsContract.ShowEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("Not implemented yet");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;

        if (selection == null) selection = "1";

        switch (sUriMatcher.match(uri)) {
            case CODE_SHOWS:
                numRowsDeleted = mShowHelper.getWritableDatabase().delete(
                        ShowsContract.ShowEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new RuntimeException("Not implemented yet");
    }
}
