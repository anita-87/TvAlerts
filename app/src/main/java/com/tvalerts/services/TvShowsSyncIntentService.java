package com.tvalerts.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.tvalerts.tasks.TvShowsSyncTask;

/**
 * A {@link IntentService} subclass for handling asynchronous task request in a service
 * on a separate handler thread.
 */

public class TvShowsSyncIntentService extends IntentService {

    /**
     * Constructor for the class that calls super and passes the name of this class.
     */
    public TvShowsSyncIntentService() {
        // TODO: update this to not use the name as a string
        super("TvShowsSyncIntentService");
    }

    /**
     * Method that gets invoked on the worker thread with a request to process.
     * @param intent The value passed to startService(Intent)
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        TvShowsSyncTask.syncTvShows(this);
    }
}
