package com.tvalerts.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.marcohc.robotocalendar.RobotoCalendarView;
import com.tvalerts.adapters.EpisodeListAdapter;
import com.tvalerts.dialogs.EpisodeDetailsDialog;
import com.tvalerts.domain.Episode;
import com.tvalerts.interfaces.EpisodeSearchResponse;
import com.tvalerts.tasks.EpisodeSearchAsyncTask;
import com.tvalerts.utils.Constants;
import com.tvalerts.R;
import com.tvalerts.dialogs.AddShowsDialog;
import com.tvalerts.utils.DatesUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Anita on 9/07/15.
 */
public class MainActivity extends AppCompatActivity implements RobotoCalendarView.RobotoCalendarListener, AddShowsDialog.AddShowsDialogListener, EpisodeSearchResponse {

    private static final String TAG = "MainActivity";

    private RobotoCalendarView robotoCalendarView;
    private TextView currentDateTextView;
    private ListView episodeListView;
    private EpisodeListAdapter episodeListAdapter;
    private int currentMonthIndex;
    private int currentYear;
    private Calendar currentCalendar;
    private Date selectedDate;
    private Date currentDate;

    private Map<String, List<Episode>> monthlyEpisodes;

    @Override
    public  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Setup the list view for the episodes
        episodeListView = (ListView) findViewById(R.id.currentDayEpisodes);
        episodeListAdapter = new EpisodeListAdapter(this);
        episodeListView.setAdapter(episodeListAdapter);
        episodeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayEpisodeDetailsDialog(position);
            }
        });

        //Gets the calendar from the view
        robotoCalendarView = (RobotoCalendarView) findViewById(R.id.robotoCalendarPicker);

        //Set listener, in this case, the same activity
        robotoCalendarView.setRobotoCalendarListener(this);

        //Initialize the RobotCalendarPicker with the current index and date
        currentCalendar = Calendar.getInstance();
        currentMonthIndex = currentCalendar.get(Calendar.MONTH);
        currentYear = currentCalendar.get(Calendar.YEAR);

        //Mark current day
        robotoCalendarView.markDayAsCurrentDay(currentCalendar.getTime());

        //OnCreate mark the selectedDate to the current day
        this.selectedDate = currentCalendar.getTime();
        //The currentDate must be initialize to the actual date
        this.currentDate = this.selectedDate;

        //Gets the currentDateTextView from the view and set the date
        currentDateTextView = (TextView) findViewById(R.id.currentDayText);
        this.setCurrentDateText(currentCalendar.getTime());

        //Toolbar settings
        Toolbar toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        //Initialize the monthly episode list
        monthlyEpisodes = new ArrayMap<String, List<Episode>>();

        //Create the asyncTask
        EpisodeSearchAsyncTask asyncTask = new EpisodeSearchAsyncTask(this, this.loadStoredShows(), currentMonthIndex+1, currentYear);
        asyncTask.delegate = this;
        asyncTask.execute();
    }

    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onDateSelected(Date date) {
        //Mark calendar date
        robotoCalendarView.markDayAsSelectedDay(date);

        //Change the text of the currentDate to the selected one
        this.setCurrentDateText(date);

        //update the selectedDate
        this.selectedDate = date;

        //Update the episodes for that date
        this.episodeListAdapter.updateEntries(new ArrayList<Episode>(this.monthlyEpisodes.get(DatesUtil.dateWithSimpleFormat(this.selectedDate))));
    }

    @Override
    public void onRightButtonClick() {
        updateCurrentMonthIndex("+");
        updateCalendar();
    }

    @Override
    public void onLeftButtonClick() {
        updateCurrentMonthIndex("-");
        updateCalendar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Inflate the menu; this adds items to the action bar if its present
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.add_show:
                FragmentManager fragmentManager = getSupportFragmentManager();
                AddShowsDialog addShowsDialog = new AddShowsDialog();
                addShowsDialog.show(fragmentManager, "addShowDialog");
                return true;
            case R.id.my_shows:
                this.displayMyShowsViews();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFinishedAddShowsDialog(String show) {
        //The listener of the added show will start the ShowResultsActivity
        //This activity searched for the text in the TvMaze API
        Intent intent = new Intent(this, ShowResultsActivity.class);
        intent.putExtra("show", show);
        startActivity(intent);
    }

    @Override
    public void onFinishedEpisodeSearch(Map<String, List<Episode>> episodes) {
        Log.d(TAG, "processResponse - total episodes retrieve: " + episodes.size());
        this.monthlyEpisodes = episodes;
        this.paintEpisodesInCalendar(this.monthlyEpisodes);
        this.episodeListAdapter.clear();
        this.episodeListAdapter.updateEntries(new ArrayList<Episode>(this.monthlyEpisodes.get(DatesUtil.dateWithSimpleFormat(this.selectedDate))));

    }

    private void updateCalendar(){
        currentCalendar.set(Calendar.MONTH, currentMonthIndex);
        currentCalendar.set(Calendar.YEAR, currentYear);

        if ((DatesUtil.getMonthIndex(this.currentDate) - 1) != currentMonthIndex){
            this.selectedDate = DatesUtil.firstDayOfMonth(currentCalendar.get(Calendar.MONTH) + 1 , currentCalendar.get(Calendar.YEAR));
        } else
            this.selectedDate = new Date();

        robotoCalendarView.initializeCalendar(currentCalendar);
        this.setCurrentDateText(this.selectedDate);

        //Create the asyncTask
        EpisodeSearchAsyncTask asyncTask = new EpisodeSearchAsyncTask(this, this.loadStoredShows(), currentMonthIndex+1, currentYear);
        asyncTask.delegate = this;
        asyncTask.execute();
    }

    private Map<String, String> loadStoredShows(){
        SharedPreferences storedShows = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        Map<String, String> storedShowsBasicInfo = (Map<String, String>)storedShows.getAll();
        return storedShowsBasicInfo;
    }

    private void paintEpisodesInCalendar(Map<String, List<Episode>> episodes){
        for(Map.Entry<String, List<Episode>> entry : episodes.entrySet()){
            Date date = DatesUtil.stringToDate(entry.getKey());
            //Only paint the date if it has any show associated.
            if ((entry.getValue() != null) && (entry.getValue().size() != 0)){
                robotoCalendarView.markFirstUnderlineWithStyle(RobotoCalendarView.BLUE_COLOR, date);
            }
        }
    }

    private void setCurrentDateText(Date date){
        currentDateTextView.setText(DatesUtil.dateWithFormat(date));
    }

    private void displayEpisodeDetailsDialog(int position){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("episodeDetailsDialog");
        if(prev != null){
            ft.remove(prev);
        }

        ft.addToBackStack(null);

        //Create and show the episodeDetails Dialog
        Episode episode = (Episode) this.episodeListAdapter.getItem(position);
        EpisodeDetailsDialog episodeDetailsDialog = EpisodeDetailsDialog.newInstance(episode);
        episodeDetailsDialog.show(ft, "episodeDetailsDialog");

    }

    private void displayMyShowsViews(){
        Log.d(TAG, "displayMyShowsViews");
        Intent intent = new Intent(this, MyShowsActivity.class);
        HashMap<String, String> loadedShows = (HashMap<String, String>) loadStoredShows();
        intent.putExtra("loadedShows", loadedShows);
        startActivity(intent);
    }

    private void updateCurrentMonthIndex(String operator){
        if (operator.equals("+")) {
            this.currentMonthIndex += 1;
            if (this.currentMonthIndex == 12) {
                this.currentMonthIndex = 0;
                this.currentYear++;
            }
        }
        if (operator.equals("-")) {
            this.currentMonthIndex -= 1;
            if (this.currentMonthIndex == -1) {
                this.currentMonthIndex = 11;
                this.currentYear--;
            }
        }

    }
}
