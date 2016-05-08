package com.tvalerts.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.tvalerts.R;
import com.tvalerts.domain.Show;
import com.tvalerts.wrappers.ShowSearchResultWrapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anita on 23/01/16.
 */
public class SearchShowAsyncTask extends AsyncTask<Void, Void, List<ShowSearchResultWrapper>>{

    private static final String TAG = "SearchShowAsyncTask";
    private static final String URL = "http://api.tvmaze.com/search/shows?q={show}";

    private Context context;
    private ProgressDialog progressDialog;
    private ArrayAdapter<Show> arrayAdapter;
    private String showToSearch;

    public SearchShowAsyncTask(Context context, ArrayAdapter<Show> arrayAdapter, String showToSearch){
        this.context = context;
        this.arrayAdapter = arrayAdapter;
        this.showToSearch = showToSearch;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.obtaining_results));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }


    @Override
    protected List<ShowSearchResultWrapper> doInBackground(Void... params) {
        try {
            Log.d(TAG, "Searching show: " + showToSearch);
            return this.searchShow();
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<ShowSearchResultWrapper> results){
        progressDialog.dismiss();
        List<Show> shows = new ArrayList<Show>();

        Iterator<ShowSearchResultWrapper> iterator = results.iterator();
        while (iterator.hasNext()){
            ShowSearchResultWrapper element = iterator.next();
            shows.add(element.getShow());
        }

        arrayAdapter.clear();
        arrayAdapter.addAll(shows);
        arrayAdapter.notifyDataSetChanged();
    }

    private List<ShowSearchResultWrapper> searchShow(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ShowSearchResultWrapper[] results = restTemplate.getForObject(URL, ShowSearchResultWrapper[].class, showToSearch);
        Log.i(TAG, "Total results - " + results.length);
        return Arrays.asList(results);
    }
}
