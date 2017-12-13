package com.tvalerts.tasks;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.tvalerts.network.TvMazeClient;
import com.tvalerts.data.TvShowContract.*;

/**
 * Class that performs the network request to sync the Tv shows and inserts them into the database.
 */

public class TvShowsSyncTask {

    /**
     * Simple tag for loggin purposes
     */
    private static final String TAG = TvShowsSyncTask.class.getSimpleName();

    /**
     * Method that perform the actual sync of the Tv shows.
     * Uses the TvMazeClient to retrieve all the shows from the Tv Maze REST API.
     * @param context Used to access the ContentResolver
     */
    synchronized public static void syncTvShows(Context context) {
        try {
            ContentValues[] tvShowsValues = TvMazeClient.getAllTvShows();

            if (tvShowsValues != null && tvShowsValues.length != 0) {
                ContentResolver tvAlertsContentResolver = context.getContentResolver();

                tvAlertsContentResolver.delete(
                        TvShowEntry.CONTENT_URI,
                        null,
                        null
                );

                tvAlertsContentResolver.bulkInsert(
                        TvShowEntry.CONTENT_URI,
                        tvShowsValues
                );
            }
        } catch (Exception e) {
            Log.e(TAG, "There was an error syncing the Tv shows. " +  e.getMessage());
        }
    }

}
