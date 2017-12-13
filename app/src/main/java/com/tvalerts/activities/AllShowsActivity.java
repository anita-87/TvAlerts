package com.tvalerts.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.tvalerts.R;
import com.tvalerts.data.TvShowContract;
import com.tvalerts.utils.TvAlertsSyncUtils;

public class AllShowsActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = AllShowsActivity.class.getSimpleName();

    private static final int ID_TV_SHOWS_LOADER = 44;

    private static final String[] MAIN_TV_SHOWS_PROJECTION = {
            TvShowContract.TvShowEntry.COLUMN_SHOW_NAME,
            TvShowContract.TvShowEntry.COLUMN_SHOW_URL,
            TvShowContract.TvShowEntry.COLUMN_SHOW_IMAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_shows);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportLoaderManager().initLoader(ID_TV_SHOWS_LOADER, null, this);
        TvAlertsSyncUtils.initialize(this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
        switch (loaderId) {
            case ID_TV_SHOWS_LOADER:
                return new CursorLoader(this,
                        TvShowContract.TvShowEntry.CONTENT_URI,
                        MAIN_TV_SHOWS_PROJECTION,
                        null,
                        null,
                        null
                );
            default:
                throw new RuntimeException("Loader not implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "Total number of shows retrived is: " + cursor.getCount());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderRest");
    }

}
