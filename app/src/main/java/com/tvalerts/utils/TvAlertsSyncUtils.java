package com.tvalerts.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.tvalerts.data.TvShowContract;
import com.tvalerts.services.TvShowsSyncIntentService;

/**
 * Class that holds all the methods used to perform Sync tasks
 */

public class TvAlertsSyncUtils {

    private static boolean sInitialized;

    synchronized public static void initialize(@NonNull final Context context) {
        if (sInitialized) return;

        sInitialized = true;

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                String[] projectionColumns = {TvShowContract.TvShowEntry._ID};
                Cursor cursor = context.getContentResolver().query(
                        TvShowContract.TvShowEntry.CONTENT_URI,
                        projectionColumns,
                        null,
                        null,
                        null
                );

                if (cursor == null || cursor.getCount() == 0) {
                    startImmediateSync(context);
                }
                cursor.close();
                return null;
            }
        }.execute();
    }

    /**
     * Helper method to perform a sync immediately using an IntentService
     * for asynchronous execution
     * @param context The Context used to start the IntentService for the sync.
     */
    public static void startImmediateSync(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, TvShowsSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }

}
