package com.tvalerts.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.tvalerts.R;
import com.tvalerts.domain.Show;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anita on 24/04/16.
 */
public class RetrieveShowsInformationAsyncTask extends AsyncTask<String[], Void, List<Show>> {

    private static final String TAG = "GetShowsInfoAsyncTask";
    private static final String URL = "http://api.tvmaze.com/shows/{showId}";

    private Context context;
    private ArrayAdapter<Show> showArrayAdapter;
    private ProgressDialog progressDialog;
    private List<Show> shows;

    public RetrieveShowsInformationAsyncTask(Context context, ArrayAdapter<Show> showArrayAdapter){
        this.context = context;
        this.showArrayAdapter = showArrayAdapter;
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
    protected List<Show> doInBackground(String[]... params) {
        try {
            String[] showIds = params[0];
            shows = new ArrayList<Show>();
            for (int i=0; i<showIds.length; i++){
                this.searchShow(showIds[i]);
            }
            return shows;
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Show> shows) {
        progressDialog.dismiss();
        showArrayAdapter.clear();
        showArrayAdapter.addAll(shows);
        showArrayAdapter.notifyDataSetChanged();
    }

    private void searchShow(String showId){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Show result = restTemplate.getForObject(URL,Show.class, showId);
        this.shows.add(result);
    }
}
