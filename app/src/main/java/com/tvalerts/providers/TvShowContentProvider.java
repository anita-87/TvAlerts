package com.tvalerts.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tvalerts.data.TvShowContract;
import com.tvalerts.data.TvShowContract.*;
import com.tvalerts.data.TvShowDbHelper;

import static com.tvalerts.data.TvShowContract.TvShowEntry.TABLE_NAME;

/**
 * This class is a ContentProvider for the TvShows read from the TvMaze REST API.
 */

public class TvShowContentProvider extends ContentProvider {

    // It's a convention to use 100, 200, 300 for directories
    // and related ints (101, 102, ...) for items in the directory

    /**
     * Constant value for directory shows.
     */
    public static final int SHOWS = 100;
    /**
     * Constant value for single show item
     */
    public static final int SHOW_WITH_ID = 101;
    /**
     * Static member variable to access the UriMatcher in the Content Provider.
     */
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    /**
     * Static method that associates URI's with their int match
     * @return The UriMatcher that match the defined URIs in these Content Provider
     */
    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Match for the whole Shows directory
        uriMatcher.addURI(TvShowContract.AUTHORITY, TvShowContract.PATH_SHOWS, SHOWS);
        // Match for a single item show
        uriMatcher.addURI(TvShowContract.AUTHORITY, TvShowContract.PATH_SHOWS + "/#", SHOW_WITH_ID);

        return uriMatcher;
    }
    /**
     * Member variable for a TvShowDbHelper that's initialized in the onCreate() method.
     */
    private TvShowDbHelper mTvShowDbHelper;

    /**
     * Method that handles the initialization of the Content Provider on startup.
     * @return true if the provider was successfully loaded, false otherwise
     */
    @Override
    public boolean onCreate() {
        Context context = getContext();
        mTvShowDbHelper = new TvShowDbHelper(context);
        return true;
    }

    /**
     * Method that handles request to insert a new row.
     * @param uri TThe content:// URI of the insertion request. This must not be null.
     * @param values A set of column_name/value pairs to add to the database. This must not be null.
     * @return The Uri for the newly inserted item. This may be null.
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mTvShowDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match) {
            case SHOWS:
                long id = db.insert(TABLE_NAME, null, values);
                if (id > 0) {
                    // The insert was successful
                    returnUri = ContentUris.withAppendedId(TvShowEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Notify the resolver if the Uri has been changed
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return returnUri;
    }

    /**
     * Method that handles request to insert a set of new rows, or the default implementation that
     * will iterate over the values and call insert(Uri, ContentValues) on each of them.
     * @param uri The content:// URI of the insertion request. This value must never be null.
     * @param values An array of sets of column_name/value pairs to add to the database. This must not be null.
     * @return The number of values that were inserted.
     */
    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        SQLiteDatabase db = mTvShowDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);


        switch (match) {
            case SHOWS:
                // We create a transaction, a way to mark the start of a large data transfer.
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    // Try to insert all the values
                    for (ContentValues value: values) {
                        long _id = db.insert(TABLE_NAME,
                                null,
                                value
                        );
                        // Only update the rows inserted if an actual row was inserted
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    // Mark the transaction as successful
                    db.setTransactionSuccessful();
                } finally {
                    // Executes after the try is completed
                    // Mark the end of the large data transfer or transaction
                    db.endTransaction();
                }
                // Notify the change if the rows inserted is larger than 0
                if (rowsInserted > 0 && getContext() != null) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
            default:
                return super.bulkInsert(uri, values);
        }

    }

    /**
     * Method that handles query request from clients with support for cancellation.
     * @param uri The URI to query.
     * @param projection The list of columns to put into the cursor. If null all columns are included.
     * @param selection A selection criteria to apply when filtering rows. If null then all rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by the values
     *                      from selectionArgs, in order that they appear in the selection.
     *                      The values will be bound as Strings. This value may be null.
     * @param sortOrder How the rows in the cursor should be sorted. If null then the provider is free to define the sort order.
     * @return a Cursor or null.
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mTvShowDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);

        Cursor retCursor;
        switch (match) {
            case SHOWS:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case SHOW_WITH_ID:
                // We will use the selection and selectionArgs
                // First we need to retrieve the id for the query
                // URI: content://<authority>/shows/#
                String id = uri.getPathSegments().get(1); // we use 1, since the 0 is the shows.
                // Selection is the _ID column = ?
                String mSelection = "_id=?";
                // Selection args = the row ID from the URI
                String[] mSelectionArgs = new String[]{id};

                retCursor = db.query(TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (getContext() != null){
            retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return retCursor;
    }

    /**
     * Method that handles requests to delete one or more rows.
     * @param uri The full URI to query, including a row ID (if a specific record is requested).
     *            This value must never be null.
     * @param selection An optional restriction to apply to rows when deleting. This value may be null.
     * @param selectionArgs This value may be null.
     * @return The number of rows affected.
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mTvShowDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int showsDeleted;

        switch (match) {
            case SHOW_WITH_ID:
                // Get the id of the row to be delted
                // URI: content://<authority>/shows/#
                String id = uri.getPathSegments().get(1); // we use 1, since the 0 is the shows.
                // Selection is the _ID column = ?
                String mSelection = "_id=?";
                // Selection args = the row ID from the URI
                String[] mSelectionArgs = new String[]{id};
                showsDeleted = db.delete(TABLE_NAME,
                        mSelection,
                        mSelectionArgs
                );
                break;
            case SHOWS:
                showsDeleted = db.delete(TABLE_NAME,
                        null,
                        null
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Only notify if at least one row was deleted
        if (showsDeleted > 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return showsDeleted;
    }

    /**
     * Method that handles request to update one or more rows.
     * Take into account that this method is not implemented for the 'TvShowContentProvider',
     * since its not needed by the application.
     * @param uri The URI to query. This can potentially have a record ID if this is an update request for a specific record.
     *            This value must never be null.
     * @param values A set of column_name/value pairs to update in the database. This must not be null.
     * @param selection An optional filter to match rows to update. This value may be null.
     * @param selectionArgs This value may be null.
     * @return the number of rows affected.
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("This method is not implemented for the TvShowContentProvider");
    }

    /**
     * Method that handles requests for the MIME type of the data at a given URI.
     * Take into account that this method is not implemented for the 'TvShowContentProvider'
     * since its not needed by the application.
     * @param uri the URI to query. This value must never be null.
     * @return a MIME type string, or null if there is no type.
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("This method is not implemented for the TvShowContentProvider");
    }
}
