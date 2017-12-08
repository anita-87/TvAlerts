package com.tvalerts.utils;

import android.content.ContentValues;
import android.util.Log;

import com.tvalerts.data.ShowsContract;
import com.tvalerts.domain.Show;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Util class used to conect with the TV-Maze API
 * Created by anita on 6/05/17.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String SHOWS_URL = "http://api.tvmaze.com/shows?page=";

    private static RestTemplate restTemplate;

    public static ContentValues[] getAllShows() {
        initRestTemplate();
        List<Show> result = new ArrayList<>();
        boolean moreResults = true;
        int page = 1;
        Log.d(TAG, "Starting the search");
        while (moreResults) {
            try {
                Show[] shows = restTemplate.getForObject(SHOWS_URL + page, Show[].class);
                result.addAll(Arrays.asList(shows));
                page++;
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                    moreResults = false;
            }
        }
        Log.d(TAG, "Shows received: " + result.size());
        return parseShowsToContentValues(result);
    }

    private static void initRestTemplate() {
        if (restTemplate == null)
            restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    private static ContentValues[] parseShowsToContentValues(List<Show> showList) {
        ContentValues[] result = new ContentValues[showList.size()];
        int i = 0;
        for (Show show : showList) {
            ContentValues showValues = new ContentValues();
            showValues.put(ShowsContract.ShowEntry.COLUMN_SHOW_API_ID, show.getId());
            showValues.put(ShowsContract.ShowEntry.COLUMN_SHOW_NAME, show.getName());
            showValues.put(ShowsContract.ShowEntry.COLUMN_SHOW_URL, show.getUrl());
            showValues.put(ShowsContract.ShowEntry.COLUMN_SHOW_TYPE, show.getType());
            showValues.put(ShowsContract.ShowEntry.COLUMN_SHOW_LANGUAGE, show.getLanguage());
            showValues.put(ShowsContract.ShowEntry.COLUMN_SHOW_STATUS, show.getStatus());
            showValues.put(ShowsContract.ShowEntry.COLUMN_SHOW_PREMIERED, show.getPremiered());
            result[i] = showValues;
            i++;
        }
        return result;
    }
}
