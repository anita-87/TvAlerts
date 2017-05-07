package com.tvalerts.utils;

import android.util.Log;

import com.tvalerts.domain.Show;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Util class used to conect with the TV-Maze API
 * Created by anita on 6/05/17.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String SHOWS_URL = "http://api.tvmaze.com/shows?page=";

    private static RestTemplate restTemplate;

    public static List<Show> getAllShows() {
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
        return result;
    }

    private static void initRestTemplate() {
        if (restTemplate == null)
            restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }
}
