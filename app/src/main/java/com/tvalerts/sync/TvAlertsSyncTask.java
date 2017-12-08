package com.tvalerts.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.tvalerts.data.ShowsContract;
import com.tvalerts.domain.Show;
import com.tvalerts.utils.NetworkUtils;

import java.util.List;

/**
 * Created by anita on 22/06/17.
 */

public class TvAlertsSyncTask {

    synchronized public static void syncShows(Context context) {
        try {
            ContentValues[] shows = NetworkUtils.getAllShows();

            if (shows != null && shows.length != 0) {
                ContentResolver tvAlertsContentResolver = context.getContentResolver();

                // Delete all data from the database
                tvAlertsContentResolver.delete(
                        ShowsContract.ShowEntry.CONTENT_URI,
                        null,
                        null
                );
                // Insert all new data into the database
                tvAlertsContentResolver.bulkInsert(
                        ShowsContract.ShowEntry.CONTENT_URI,
                        shows
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
