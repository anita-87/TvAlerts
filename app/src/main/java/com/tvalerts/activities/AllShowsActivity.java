package com.tvalerts.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tvalerts.R;
import com.tvalerts.adapters.TvShowAdapter;
import com.tvalerts.mappers.ShowMapper;
import com.tvalerts.mappers.ShowSearchMapper;
import com.tvalerts.network.TvMazeClient;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Activity that shows all the shows available in the Tv Maze API.
 * Uses a RecyclerView to render the shows.
 */
public class AllShowsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener {
    /**
     * Simple TAG for logging purposes
     */
    private static final String TAG = AllShowsActivity.class.getSimpleName();
    /**
     * Instance of the custom adapter for the Tv show information
     */
    private TvShowAdapter mShowsAdapter;
    /**
     * Global variable to hold a reference to the SearchView to access it by several methods.
     */
    private SearchView searchView = null;

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

        new LoadTvShowsByPage(this).execute(1);
    }

    /**
     * Method called when creating the elements of the menu.
     * It sets the listeners to the menu operations, to search shows and close the search.
     * @param menu - the menu instance included in the activity.
     * @return true - for the menu to be displayed in the activity.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu search in the menu
        getMenuInflater().inflate(R.menu.search_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.search_show);
        this.searchView = (SearchView) searchItem.getActionView();
        this.searchView.setOnQueryTextListener(this);
        ImageView closeButton =
                this.searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        closeButton.setOnClickListener(this);
        return true;
    }

    /**
     * Method that handles the 'search' event from the SearchView.
     * @param query - the string the user input in to search for the show.
     * @return true - since the action was handled by the listener.
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "Searched test is: " + query);
        new LoadTvShowsByQuery(this).execute(query);
        this.searchView.clearFocus();
        return true;
    }

    /**
     * Method that handles the 'search' event everytime the input text changes.
     * This is not implemented for this activity.
     * @param newText - the new text introduced by the user.
     * @return false - since the action is not handled by the listener.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    /**
     * Method that handles the click on the 'close' button of the SearchView.
     * Clears the query text and clears the focus from the SearchView.
     * @param view - the SearchView that perfomed the action. In this case the SearchView.
     */
    @Override
    public void onClick(View view) {
        Log.d(TAG, "SearchView clear button clicked");
        this.searchView.setQuery("", false);
        this.searchView.clearFocus();
        new LoadTvShowsByPage(this).execute(1);
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
    public static class LoadTvShowsByPage extends AsyncTask<Integer, Void, List<ShowMapper>> {
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
            this.activityWeakReference = new WeakReference<>(context);
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
                RecyclerView recyclerView = activity.findViewById(R.id.rv_tv_shows);
                TextView textView = activity.findViewById(R.id.tv_all_shows_error_message);
                textView.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
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
        protected List<ShowMapper> doInBackground(Integer... params) {
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
        protected void onPostExecute(List<ShowMapper> result) {
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
                        errorMessage.setText(R.string.tv_maze_api_not_shows_found);
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    errorMessage.setText(R.string.tv_maze_api_connection_error);
                    errorMessage.setVisibility(View.VISIBLE);
                }
            } else {
                Log.e(TAG, "There was an error trying to get the reference to the activity");
            }
        }

    }

    /**
     * Inner class that performs and Async call to obtain the Tv shows given a query introduced by the user.
     * It performs a search with the given query at Tv Maze REST API.
     * It extends the AsyncTask class to do the remote call in a background thread.
     */
    public static class LoadTvShowsByQuery extends AsyncTask<String, Void, List<ShowSearchMapper>> {
        /**
         * Simple TAG for logging purposes
         */
        private final String TAG = LoadTvShowsByQuery.class.getSimpleName();
        /**
         * Instance to get a reference to the activity that launches the task.
         * This is done to avoid possible leaks.
         */
        private WeakReference<AllShowsActivity> activityWeakReference;

        /**
         * Public constructor for the class
         * @param context The context of the class. It should be the activity calling it.
         */
        LoadTvShowsByQuery(AllShowsActivity context) {
            this.activityWeakReference = new WeakReference<>(context);
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
                RecyclerView recyclerView = activity.findViewById(R.id.rv_tv_shows);
                TextView textView = activity.findViewById(R.id.tv_all_shows_error_message);
                textView.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            } else {
                Log.e(TAG, "There was an error trying to get the reference to the activity");
            }
        }

        /**
         * Method that performs the REST API call to obtain the shows by the search query.
         * This is performed in a background thread to avoid blocking the view.
         *
         * @param params array containing the parameters passed to the method.
         * @return the list of shows for the page called or null if there was a connection problem.
         */
        @Override
        protected List<ShowSearchMapper> doInBackground(String... params) {
            String query = params[0];
            try {
                return TvMazeClient.queryTvShows(query);
            } catch (Exception e) {
                Log.e(TAG, "There was an error querying the shows." + e.getMessage());
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
                    if (result.size() > 0) {
                        TvShowAdapter adapter = activity.getShowsAdapter();
                        final List<ShowMapper> showList = new ArrayList<>();
                        for(ShowSearchMapper showSearch : result) {
                            showList.add(showSearch.getShow());
                        }
                        adapter.setShowsList(showList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.INVISIBLE);
                        errorMessage.setText(R.string.tv_maze_api_not_shows_found);
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
