package com.tvalerts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tvalerts.R;
import com.tvalerts.domain.Show;
import com.tvalerts.tasks.SearchShowAsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anita on 17/01/16.
 */
public class ShowResultsActivity extends AppCompatActivity {

    private static final String TAG = "ShowResultsActivity";

    private String showToSearch;
    private ArrayAdapter<Show> arrayAdapter;
    private List<Show> showsResults;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        //Initialize the showResults so is empty when it starts
        this.showsResults = new ArrayList<Show>();
        //Initialize the arrayAdapter to the 'show_list_item' view with the 'showResults' array
        this.arrayAdapter = new ArrayAdapter<Show>(this, R.layout.show_list_item, R.id.showResult, showsResults);
        //Set the view for the Activity
        setContentView(R.layout.show_results_activity);

        //Set the properties for the listView and the ArrayAdapter
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setTextFilterEnabled(true);
        listView.setAdapter(this.arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Show selectedShow = showsResults.get(position);
                Intent intent = new Intent(ShowResultsActivity.this, ShowActivity.class);
                intent.putExtra("show", selectedShow);
                startActivity(intent);
            }
        });

        //Retrieve the show to search passed by the 'MainActivity'
        Intent intent = getIntent();
        this.showToSearch = intent.getStringExtra("show");

        //Add the Toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        //Execute the AsyncTask to search for the shows
        new SearchShowAsyncTask(this, arrayAdapter, showToSearch).execute();

    }


}
