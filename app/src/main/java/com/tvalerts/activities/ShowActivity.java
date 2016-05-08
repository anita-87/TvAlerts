package com.tvalerts.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tvalerts.dialogs.AddShowsDialog;
import com.tvalerts.utils.Constants;
import com.tvalerts.R;
import com.tvalerts.adapters.PagerAdapter;
import com.tvalerts.adapters.PersonListAdapter;
import com.tvalerts.domain.Show;
import com.tvalerts.enums.Status;

import java.util.Map;

/**
 * Created by Anita on 24/01/16.
 */
public class ShowActivity extends AppCompatActivity {

    private static final String TAG = "ShowActivity";

    private Toolbar toolbar;
    private TabLayout tabLayout;

    private Show show;
    private PersonListAdapter arrayAdapter;
    private boolean isLoadedShow;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        //Setup the content view
        setContentView(R.layout.show_activity);

        //Toolbar settings
        toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        //Setup the TabLayout
        setUpTabLayout();

        //Store the selected show
        Intent intent = getIntent();
        this.show = intent.getParcelableExtra("show");
        Log.i(TAG, "Show selected is: " + show.getName());

        //Initialize the cast so that is empty when we start
        this.arrayAdapter = new PersonListAdapter(this);

        this.isLoadedShow = intent.getBooleanExtra("isSavedShow", false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Inflate the menu; this adds items to the action bar if its present
        if(isLoadedShow) {
            getMenuInflater().inflate(R.menu.show_menu, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete:
                this.deleteShow();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public Show getShow(){
        return this.show;
    }

    public PersonListAdapter getArrayAdapter(){ return  this.arrayAdapter; }

    public void saveShow(){

        //Only save the show if it's running, since it makes no sense to store a not running show
        if(this.getShow().getStatus() == Status.RUNNING){
            Log.i(TAG, "Storing the show '"+this.show.getId()+"' into SharedPreferences file");
            SharedPreferences storedShows = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor sharedPreferencesEditor = storedShows.edit();
            sharedPreferencesEditor.putString(String.valueOf(this.getShow().getId()), this.getShow().getName());
            sharedPreferencesEditor.commit();
            Toast.makeText(this, R.string.show_stored, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.show_not_stored, Toast.LENGTH_LONG).show();
        }


    }

    private void setUpTabLayout(){
        tabLayout = (TabLayout) findViewById(R.id.show_tabs);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.show));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.cast));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }

    private void deleteShow(){
        String key = String.valueOf(this.getShow().getId());
        Log.d(TAG, "Deleting saved show '"+key+"'");
        SharedPreferences storedShows = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = storedShows.edit();
        sharedPreferencesEditor.remove(key);
        sharedPreferencesEditor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
