package com.tvalerts.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.tvalerts.data.ShowsContract;

/**
 * Created by anita on 22/06/17.
 */

public class TvAlertsSyncUtils {

    private static boolean sInitialized;

    synchronized public static void initialize(@NonNull final Context context){
        if (sInitialized) return;

        sInitialized = true;

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {

                Uri showQueryUri = ShowsContract.ShowEntry.CONTENT_URI;

                // Execute a query just to check if there any show in the DB.
                String[] projectionColumns = {ShowsContract.ShowEntry._ID};

                Cursor cursor = context.getContentResolver().query(
                        showQueryUri,
                        projectionColumns,
                        null,
                        null,
                        null
                );

                // If the cursor is null it means there is no data in the DB
                // In that case we have to sync immediately
                if (cursor == cursor || cursor.getCount() == 0) {
                    startImmediateSync(context);
                }

                cursor.close();
                return null;
            }
        }.execute();
    }

    public static void startImmediateSync(@NonNull final Context context){
        Intent intentToSyncImmediately = new Intent(context, TvAlertsSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }
}




