package com.tvalerts.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by anita on 22/06/17.
 */

public class TvAlertsSyncIntentService extends IntentService {

    public TvAlertsSyncIntentService(){
        super("TvAlertsSyncIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        TvAlertsSyncTask.syncShows(this);
    }
}
