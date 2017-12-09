package com.cnleon.tvalerts.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cnleon.tvalerts.R;

/**
 * Main activity of the application.
 * This activity will hold the calendar view and also the button to add more shows
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Simple TAG for logging purposes
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Method executed when the activity is created.
     *
     * @param savedInstanceState - bundle with information to create the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton mFabAddMoreShows = findViewById(R.id.fab_add_more_shows);
        mFabAddMoreShows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAllShowsActivity();
            }
        });
    }

    private void startAllShowsActivity() {
        Log.d(TAG, "Starting AllShowsActivity");
        Intent intent = new Intent(this, AllShowsActivity.class);
        startActivity(intent);
    }
}
