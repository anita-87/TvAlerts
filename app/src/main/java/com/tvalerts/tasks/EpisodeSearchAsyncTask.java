package com.tvalerts.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.tvalerts.R;
import com.tvalerts.domain.Episode;
import com.tvalerts.domain.Show;
import com.tvalerts.interfaces.EpisodeSearchResponse;
import com.tvalerts.utils.DatesUtil;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by anita on 18/02/16.
 */
public class EpisodeSearchAsyncTask extends AsyncTask<Void, Void, Map<String, List<Episode>>> {

    private static final String TAG = "EpisodeSearchAsyncTask";
    private static final String URL = "http://api.tvmaze.com/shows/{id}/episodesbydate?date={date}";
    private static final String URL_SHOWS = "http://api.tvmaze.com/shows/{id}";

    private Context context;
    private Map<String, String> shows;
    private int currentMonth;
    private int currentYear;
    private ProgressDialog progressDialog;
    private RestTemplate restTemplate;

    public EpisodeSearchResponse delegate = null;

    public EpisodeSearchAsyncTask(Context context,Map<String, String> shows, int currentMonth, int currentYear){
        this.context = context;
        this.shows = shows;
        this.currentMonth = currentMonth;
        this.currentYear = currentYear;
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.obtaining_results));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    @Override
    protected Map<String, List<Episode>> doInBackground(Void... params) {
        try {
            return this.getEmittingEpisodes();
        } catch (Exception e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(Map<String, List<Episode>> episodesByDate) {
        super.onPostExecute(episodesByDate);
        progressDialog.dismiss();
        if(delegate != null){
            delegate.onFinishedEpisodeSearch(episodesByDate);
        } else {
            Log.e(TAG, "You have not assigned CastSearchResponse delegate");
        }
    }

    private Map<String, List<Episode>> getEmittingEpisodes(){
        Map<String, List<Episode>> result = this.initializeMapResponse();
        for (Map.Entry<String, String> showEntry : shows.entrySet()){
            //Gets all the episodes for the show and month and year
            Log.i(TAG, "Obtaining all the episodes for show: '"+showEntry.getValue()+"'");
            List<Episode> episodes = this.getAlEpisodes(showEntry.getKey());
            for (Map.Entry<String, List<Episode>> element : result.entrySet()){
                Iterator<Episode> episodeIterator = episodes.iterator();
                while (episodeIterator.hasNext()){
                    Episode episode = episodeIterator.next();
                    episode.setShow(showEntry.getValue());
                    if (DatesUtil.isSameDate(episode.getAirdate(), element.getKey())){
                        Log.d(TAG, "Adding episode '"+episode.getName()+"' for show '"+episode.getShow()+ " to date "+element.getKey());
                        element.getValue().add(episode);
                    }
                }
            }
        }
        return result;
    }

    private List<Episode> getAlEpisodes(String showId){
        Episode[] episodes = null;
        List<Episode> results = new ArrayList<Episode>();
        try {
            episodes = restTemplate.getForObject("http://api.tvmaze.com/shows/{id}/episodes", Episode[].class, showId);
            results = filterEpisodesByMonth(Arrays.asList(episodes), currentMonth, currentYear);
            return results;
        } catch (HttpClientErrorException e){
            //TODO: handle the exceptions
            Log.e(TAG, e.getMessage());
            throw e;
        }
    }

    private List<Episode> filterEpisodesByMonth(List<Episode> episodes, int currentMonth, int currentYear){
        Log.d(TAG, "Filtering out only the episodes for month '"+currentMonth+"' and year '"+currentYear+"'");
        List<Episode> result = new ArrayList<Episode>();
        Iterator<Episode> episodeIterator = episodes.iterator();
        while (episodeIterator.hasNext()){
            Episode episode = episodeIterator.next();
            if(DatesUtil.isSameMonthDate(episode.getAirdate(), currentMonth, currentYear))
                result.add(episode);
        }
        return  result;
    }

    private Map<String, List<Episode>> initializeMapResponse(){
        Map<String, List<Episode>> result = new HashMap<String, List<Episode>>();
        List<String> dates = DatesUtil.getMonthStrings(currentMonth, currentYear);
        Iterator<String> iterator = dates.iterator();
        while (iterator.hasNext()){
            String dateString = iterator.next();
            result.put(dateString, new ArrayList<Episode>());
        }
        return result;
    }
}
