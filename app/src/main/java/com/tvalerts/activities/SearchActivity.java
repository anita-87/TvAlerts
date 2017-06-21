package com.tvalerts.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FilterQueryProvider;
import android.widget.ProgressBar;

import com.cnleon.tvalerts.R;
import com.tvalerts.adapters.ShowAdapter;
import com.tvalerts.data.ShowsContract;
import com.tvalerts.domain.Show;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.tvalerts.data.ShowsContract.ShowEntry;

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, FilterQueryProvider {

    public static final String[] SHOW_PROJECTION = {
            ShowsContract.ShowEntry.COLUMN_SHOW_NAME
    };
    public static final int INDEX_SHOW_NAME = 0;
    private static final String TAG = SearchActivity.class.getSimpleName();
    private static final int ALL_SHOWS_SEARCH_LOADER = 22;
    private ProgressBar mProgressBarIndicator;
    private ShowAdapter mShowAdapter;
    private RecyclerView mShowsRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Set up the Navigation Up
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Insert Fake Data
        List<ContentValues> contentValues = new ArrayList<>();
        contentValues.add(createContentValues("65", "Bones", "http://www.tvmaze.com/shows/65/bones", "Scripted", "English", "Ended", "2005-09-13"));
        contentValues.add(createContentValues("718", "The Tonight Show Starring Jimmy Fallon",
                "http://api.tvmaze.com/shows/718", "Talk Show", "English", "Running", "2014-02-17"));
        insertFakeData(this, contentValues);
        // Init the variables
        mProgressBarIndicator = (ProgressBar) findViewById(R.id.pb_shows_loading_indicator);
        mShowsRecyclerView = (RecyclerView) findViewById(R.id.rv_shows);

        //Assign the LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mShowsRecyclerView.setLayoutManager(layoutManager);
        mShowsRecyclerView.setHasFixedSize(true);

        //Init the adapter
        mShowAdapter = new ShowAdapter(this);
        mShowAdapter.setmFilterQueryProvider(this);
        mShowsRecyclerView.setAdapter(mShowAdapter);
        //Call the showLoading method
        showLoading();

        // Initialize the loader
        getSupportLoaderManager().initLoader(ALL_SHOWS_SEARCH_LOADER, null, this);
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
        // Set up the search functionality
        MenuItem searchItem = menu.findItem(R.id.action_seach);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        search(searchView);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, final Bundle bundle) {
        switch (loaderId) {
            case ALL_SHOWS_SEARCH_LOADER:
                Uri showQueryUri = ShowsContract.ShowEntry.CONTENT_URI;
                String sortOrder = ShowsContract.ShowEntry.COLUMN_SHOW_NAME + " ASC";

                return new CursorLoader(this,
                        showQueryUri,
                        SHOW_PROJECTION,
                        null,
                        null,
                        sortOrder);

            default:
                throw new UnsupportedOperationException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mShowAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        mShowsRecyclerView.smoothScrollToPosition(mPosition);

        if (data.getCount() != 0)
            showShowsDataView();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mShowAdapter.swapCursor(null);
    }

    @Override
    public Cursor runQuery(CharSequence constraint) {
        String constraintString = constraint.toString();
        String mSelectionClause;
        String[] mSelectionArgs;
        if (constraintString.isEmpty()) {
            mSelectionClause = null;
            mSelectionArgs = null;
        } else {
            mSelectionClause = ShowEntry.COLUMN_SHOW_NAME + " LIKE ?";
            mSelectionArgs = new String[]{"%" + constraint.toString() + "%"};
        }
        return this.getContentResolver().query(ShowEntry.CONTENT_URI, SHOW_PROJECTION, mSelectionClause, mSelectionArgs, null);
    }

    private void loadAllShows() {
        // Configure the loader
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<List<Show>> allShowsSearchLoader = loaderManager.getLoader(ALL_SHOWS_SEARCH_LOADER);
        if (allShowsSearchLoader == null) {
            loaderManager.initLoader(ALL_SHOWS_SEARCH_LOADER, null, this);
        } else {
            loaderManager.restartLoader(ALL_SHOWS_SEARCH_LOADER, null, this);
        }
    }

    private void showLoading() {
        mShowsRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBarIndicator.setVisibility(View.VISIBLE);
    }

    private void showShowsDataView() {
        mProgressBarIndicator.setVisibility(View.INVISIBLE);
        mShowsRecyclerView.setVisibility(View.VISIBLE);
    }

    private void insertFakeData(Context context, List<ContentValues> contentValues) {

        List<ContentValues> fakeValues = new ArrayList<>();
        Iterator<ContentValues> iterator = contentValues.iterator();
        while (iterator.hasNext()) {
            fakeValues.add(iterator.next());
        }

        context.getContentResolver().bulkInsert(ShowEntry.CONTENT_URI,
                fakeValues.toArray(new ContentValues[contentValues.size()]));
    }

    private ContentValues createContentValues(String id, String name, String url,
                                              String type, String language, String status, String premieredDate) {
        ContentValues testShowsValues = new ContentValues();
        // Bones
        testShowsValues.put(ShowEntry.COLUMN_SHOW_API_ID, id);
        testShowsValues.put(ShowEntry.COLUMN_SHOW_NAME, name);
        testShowsValues.put(ShowEntry.COLUMN_SHOW_URL, url);
        testShowsValues.put(ShowEntry.COLUMN_SHOW_TYPE, type);
        testShowsValues.put(ShowEntry.COLUMN_SHOW_LANGUAGE, language);
        testShowsValues.put(ShowEntry.COLUMN_SHOW_STATUS, status);
        testShowsValues.put(ShowEntry.COLUMN_SHOW_PREMIERED, premieredDate);
        return testShowsValues;
    }

    public void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mShowAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

}
