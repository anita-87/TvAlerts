package com.tvalerts.utils;

import android.util.Log;

import com.tvalerts.domain.Show;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Util class used to conect with the TV-Maze API
 * Created by anita on 6/05/17.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String SHOWS_URL = "http://api.tvmaze.com/shows";

    private static RestTemplate restTemplate;

    public static List<Show> getAllShows() {
        initRestTemplate();
        Show[] shows = restTemplate.getForObject(SHOWS_URL, Show[].class);
        Log.d(TAG, "Shows received: " + shows.length);
        return Arrays.asList(shows);
    }

    private static void initRestTemplate() {
        if (restTemplate == null)
            restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }
}
