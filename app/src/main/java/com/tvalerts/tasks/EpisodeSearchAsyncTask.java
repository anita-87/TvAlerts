package com.tvalerts.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.tvalerts.R;
import com.tvalerts.domain.Episode;
import com.tvalerts.interfaces.EpisodeSearchResponse;
import com.tvalerts.utils.DatesUtil;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by anita on 18/02/16.
 */
public class EpisodeSearchAsyncTask extends AsyncTask<Void, Void, Map<String, List<Episode>>> {

    private static final String TAG = "EpisodeSearchAsyncTask";
    private static final String URL = "http://api.tvmaze.com/shows/{id}/episodesbydate?date={date}";

    private Context context;
    private Map<String, String> shows;
    private int currentMonth;
    private ProgressDialog progressDialog;
    private RestTemplate restTemplate;

    public EpisodeSearchResponse delegate = null;

    public EpisodeSearchAsyncTask(Context context,Map<String, String> shows, int currentMonth){
        this.context = context;
        this.shows = shows;
        this.currentMonth = currentMonth;
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
            return this.getEpisodesByDate();
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

    private List<Episode> getEpisodes(){
        List<String> dates = DatesUtil.getMonthStrings(currentMonth);
        List<Episode> result = new ArrayList<Episode>();
        Episode[] episodes = null;
        Map<String, List<Episode>> r = new ArrayMap<String, List<Episode>>();
        //Iterate through the shows to get the episodes
        for (Map.Entry<String, String> showEntry : shows.entrySet()){
            Iterator<String> datesIterator = dates.iterator();
            while (datesIterator.hasNext()){
                String date = datesIterator.next();
                try {
                    episodes = restTemplate.getForObject(URL, Episode[].class, showEntry.getKey(), date);
                    for(int i =0; i<episodes.length; i++){
                        episodes[i].setShow(showEntry.getValue());
                        result.add(episodes[i]);
                    }
                } catch (HttpClientErrorException e){
                    //TODO: Handle other exceptions
                    if (e.getStatusCode().value() != 404){
                        throw e;
                    }
                }
            }
        }
        return result;
    }

    private Map<String, List<Episode>> getEpisodesByDate(){
        List<String> dates = DatesUtil.getMonthStrings(currentMonth);
        Map<String, List<Episode>> result = new ArrayMap<String, List<Episode>>();
        Iterator<String> datesIterator = dates.iterator();
        //Iterate through the dates to get all the episodes by date
        while (datesIterator.hasNext()){
            String date = datesIterator.next();
            List<Episode> episodesByDate = new ArrayList<Episode>();
            Episode[] ep = null;

            for (Map.Entry<String, String> showEntry : shows.entrySet()){
                try {
                    ep = restTemplate.getForObject(URL, Episode[].class, showEntry.getKey(), date);
                    for (int i=0; i<ep.length; i++){
                        ep[i].setShow(showEntry.getValue());
                        episodesByDate.add(ep[i]);
                    }
                } catch (HttpClientErrorException e){
                    //TODO: handle tother exceptions
                    if (e.getStatusCode().value() != 404){
                        throw e;
                    }
                }
            }
            result.put(date, episodesByDate);
        }
        return result;
    }
}
