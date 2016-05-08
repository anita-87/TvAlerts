package com.tvalerts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tvalerts.R;
import com.tvalerts.domain.Show;
import com.tvalerts.tasks.RetrieveShowsInformationAsyncTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by anita on 20/04/16.
 */
public class MyShowsActivity extends AppCompatActivity {

    private static final String TAG = "MyShowsActivity";

    private Map<String, String> loadedShows;
    private String[] loadShowsIds;
    private ArrayAdapter<Show> arrayAdapter;
    private List<Show> showsResults;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        //Initialize the showResults variable
        this.showsResults = new ArrayList<Show>();

        //Initialize the arrayAdapter to the 'show_list_item' view with the 'showResults' array
        this.arrayAdapter = new ArrayAdapter<Show>(this, R.layout.show_list_item, R.id.showResult, showsResults);

        //Set the view for the Activity
        setContentView(R.layout.my_shows_activity);

        //Set the properties for the listView and the ArrayAdapter
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setTextFilterEnabled(true);
        listView.setAdapter(this.arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Show selectedShow = showsResults.get(position);
                Intent intent = new Intent(MyShowsActivity.this, ShowActivity.class);
                intent.putExtra("show", selectedShow);
                intent.putExtra("isSavedShow", true);
                startActivity(intent);
            }
        });

        //Toolbar settings
        Toolbar toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        //Retrieve the loaded shows from the intent
        Intent intent = getIntent();
        loadedShows = (Map<String, String>) intent.getSerializableExtra("loadedShows");

        //Load the show Ids
        this.calculateShowIds();

        new RetrieveShowsInformationAsyncTask(this, arrayAdapter).execute(this.loadShowsIds);
    }

    private void calculateShowIds(){
        this.loadShowsIds = new String[loadedShows.size()];
        int i = 0;
        Iterator iterator = this.loadedShows.keySet().iterator();
        while (iterator.hasNext()){
            this.loadShowsIds[i] = (String) iterator.next();
            i++;
        }
    }
}
