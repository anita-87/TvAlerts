package com.tvalerts.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tvalerts.R;
import com.tvalerts.adapters.TvShowAdapter;
import com.tvalerts.mappers.ShowSearchMapper;
import com.tvalerts.network.TvMazeClient;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Activity that shows all the shows available in the Tv Maze API.
 * Uses a RecyclerView to render the shows.
 */
public class AllShowsActivity extends AppCompatActivity {
    /**
     * Simple TAG for logging purposes
     */
    private static final String TAG = AllShowsActivity.class.getSimpleName();
    /**
     * Instance of the custom adapter for the Tv show information
     */
    private TvShowAdapter mShowsAdapter;
    /**
     * Integer that keeps count of the current page we query in the Tv Maze REST API
     */
    private int currentPage = 1;

    /**
     * Method executed when the activity is created.
     *
     * @param savedInstanceState - bundle with information to create the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_shows);
        RecyclerView mRecyclerView = findViewById(R.id.rv_tv_shows);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // The LinearLayoutManager tells the Recycler view that the view
        // must be arranged in linear fashion
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mShowsAdapter = new TvShowAdapter(this);

        new LoadTvShowsByPage(this).execute(currentPage);
    }

    /**
     * Getter method for the Tv shows adapter
     * @return the Tv shows adapter
     */
    public TvShowAdapter getShowsAdapter(){
        return mShowsAdapter;
    }

    /**
     * Inner class that performs and Async call to obtain all the Tv shows.
     * It queries the Tv Maze REST API by page.
     * It extends the AsyncTask class to do the remote call in a background thread.
     */
    public static class LoadTvShowsByPage extends AsyncTask<Integer, Void, List<ShowSearchMapper>> {
        /**
         * Simple TAG for logging purposes
         */
        private final String TAG = LoadTvShowsByPage.class.getSimpleName();

        /**
         * Instance to get a reference to the activity that launches the task.
         * This is done to avoid possible leaks.
         */
        private WeakReference<AllShowsActivity> activityWeakReference;

        /**
         * Public constructor for the class
         * @param context The context of the class. It should be the activity calling it.
         */
        LoadTvShowsByPage(AllShowsActivity context) {
            this.activityWeakReference = new WeakReference<AllShowsActivity>(context);
        }

        /**
         * Method that gets executed before the background thread is launched.
         * Is used to adapt the view, showing the progress bar in the view.
         */
        @Override
        protected void onPreExecute() {
            AllShowsActivity activity = activityWeakReference.get();
            if (activity != null) {
                ProgressBar progressBar = activity.findViewById(R.id.pb_shows_by_page);
                progressBar.setVisibility(View.VISIBLE);
            } else {
                Log.e(TAG, "There was an error trying to get the reference to the activity");
            }
        }

        /**
         * Method that performs the REST API call to obtain the shows by page.
         * This is performed in a background thread to avoid blocking the view.
         *
         * @param params array containing the parameters passed to the method.
         * @return the list of shows for the page called or null if there was a connection problem.
         */
        @Override
        protected List<ShowSearchMapper> doInBackground(Integer... params) {
            int page = params[0];
            try {
                return TvMazeClient.getAllTvShowsByPage(page);
            } catch (Exception e) {
                Log.e(TAG, "There was an error fetching the shows by page. " + e.getMessage());
                return null;
            }
        }

        /**
         * Method that gets executed when the results are returned to the activity.
         * It updates the recycler view and hides the progress bar.
         * It shows an error message in case there was any problem.
         *
         * @param result the list of shows obtained from the Tv Maze REST API.
         */
        @Override
        protected void onPostExecute(List<ShowSearchMapper> result) {
            AllShowsActivity activity = activityWeakReference.get();
            if (activity != null) {
                ProgressBar progressBar = activity.findViewById(R.id.pb_shows_by_page);
                RecyclerView recyclerView = activity.findViewById(R.id.rv_tv_shows);
                TextView errorMessage = activity.findViewById(R.id.tv_all_shows_error_message);
                progressBar.setVisibility(View.INVISIBLE);
                if (result != null) {
                    if (result.size() > 0){
                        TvShowAdapter adapter = activity.getShowsAdapter();
                        adapter.setShowsList(result);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.INVISIBLE);
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    errorMessage.setVisibility(View.VISIBLE);
                }
            } else {
                Log.e(TAG, "There was an error trying to get the reference to the activity");
            }
        }

    }

}
