package com.tvalerts.activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cnleon.tvalerts.R;
import com.tvalerts.adapters.ShowAdapter;
import com.tvalerts.domain.Show;
import com.tvalerts.utils.NetworkUtils;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();
    private static final int NUM_LIST_ITEMS = 100;

    private TextView mErrorMessageDisplay;
    private ProgressBar mProgressBarIndicator;
    private ShowAdapter mShowAdapter;
    private RecyclerView mShowsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Set up the Navigation Up
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Init the variables
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mProgressBarIndicator = (ProgressBar) findViewById(R.id.pb_shows_loading_indicator);
        mShowsList = (RecyclerView) findViewById(R.id.rv_shows);
        //Assign the LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mShowsList.setLayoutManager(layoutManager);
        mShowsList.setHasFixedSize(true);
        //Create the adapter
        mShowAdapter = new ShowAdapter();
        //Conect the LayoutManager with the adapter
        mShowsList.setAdapter(mShowAdapter);
        // Load all the shows from the API
        loadAllShows();
    }

    /**
     * Method that Creates the Options in the menu
     *
     * @param menu - the menu to be created
     * @return true, since the menu was created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search, menu);
        // TODO: investigate if there is a better method to do this:
        // updating the color of the icon to white.
        Drawable searchIcon = menu.getItem(0).getIcon();
        searchIcon.mutate();
        searchIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
        return true;
    }

    private void loadAllShows() {
        new ShowsQueryTask().execute();
    }

    private void showErrorMessage() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class ShowsQueryTask extends AsyncTask<Void, Void, List<Show>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBarIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Show> doInBackground(Void... params) {
            return NetworkUtils.getAllShows();
        }

        @Override
        protected void onPostExecute(List<Show> shows) {
            mProgressBarIndicator.setVisibility(View.INVISIBLE);
            if (shows != null) {
                mShowAdapter.setShowData(shows);
            } else
                showErrorMessage();
        }
    }
}
