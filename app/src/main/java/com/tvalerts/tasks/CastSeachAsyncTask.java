package com.tvalerts.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.tvalerts.R;
import com.tvalerts.adapters.PersonListAdapter;
import com.tvalerts.domain.Cast;
import com.tvalerts.domain.Person;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by anita on 12/02/16.
 */
public class CastSeachAsyncTask extends AsyncTask<Void, Void, List<Cast>> {

    private static final String TAG = "CastSeachAsyncTask";
    private static final String URL = "http://api.tvmaze.com/shows/{showId}/cast";

    private Context context;
    private PersonListAdapter arrayAdapter;
    private int showId;

    private ProgressDialog progressDialog;

    public CastSeachAsyncTask(Context context, PersonListAdapter arrayAdapter, int showId){
        this.context = context;
        this.arrayAdapter = arrayAdapter;
        this.showId = showId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();

    }

    @Override
    protected List<Cast> doInBackground(Void... params) {
        try {
            Log.d(TAG, "Searching for cast of show with 'id': " + this.showId);
            return this.searchCast();
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Cast> cast) {
        Log.d(TAG, "onPostExecute");
        super.onPostExecute(cast);
        progressDialog.dismiss();
        arrayAdapter.clear();
        List<Person> personsCast = new ArrayList<Person>();
        Iterator<Cast> iterator = cast.iterator();
        while (iterator.hasNext()){
            personsCast.add(iterator.next().getPerson());
        }
        arrayAdapter.updateEntries((ArrayList) personsCast);
    }

    private List<Cast> searchCast(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Cast[] results = restTemplate.getForObject(URL, Cast[].class, this.showId);
        Log.i(TAG, "Total results - " + results.length);
        return Arrays.asList(results);
    }
}
