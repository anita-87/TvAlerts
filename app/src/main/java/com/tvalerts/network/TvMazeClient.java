package com.tvalerts.network;

import android.content.ContentValues;
import android.util.Log;

import com.tvalerts.mappers.ShowSearchMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that serves as a client to the TV-Maze API to retrieve the information about the Tv Shows.
 */

public class TvMazeClient {
    /**
     * Simple tag for loggin purposes
     */
    private static final String TAG = TvMazeClient.class.getSimpleName();
    /**
     * Constant value for the protocol of the TV Maze REST API URL.
     */
    private static final String PROTOCOL = "http";
    /**
     * Constant value for the host of the TV Maze REST API URL.
     */
    private static final String HOST = "api.tvmaze.com";
    /**
     * Constant value for the path of shows in the TV Maze REST API URL.
     */
    private static final String PATH_SHOWS = "shows";
    /**
     * Template to create HTTP connections and handle errors.
     */
    private static RestTemplate mRestTemplate;

    /**
     * Method used to build the TV Maze URLs as string values.
     * @param path The relative path in the URL to consult. This value cannot be null.
     * @param query The query value for the request, for example, "page=1". This value can be null.
     * @return a string representation of a URL in the TV Maze REST API.
     */
    private static String buildStringUrl(String path, String query) {
        UriComponents uriComponents;
        if (path == null) {
            throw new IllegalArgumentException("Parameter path is mandatory");
        }
        if (query == null) {
            uriComponents = UriComponentsBuilder.newInstance()
                    .scheme(PROTOCOL).host(HOST).path(path).build();
        } else {
            uriComponents = UriComponentsBuilder.newInstance()
                    .scheme(PROTOCOL).host(HOST).path(path).query(query).build();
        }
        return uriComponents.toUriString();
    }

    /**
     * Method used to initialize the member variable mRestTemplate.
     * Adds a MappingJacksonConverter to be able to read JSON responses from the REST API.
     */
    private static void initRestTemplate() {
        if (mRestTemplate == null) {
            mRestTemplate = new RestTemplate();
        }
        mRestTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    /**
     * Methods that converts a list of shows to an array of ContentValues.
     * @param shows The list of shows obtained from the TV Maze REST API.
     * @return array of ContentValues parsed from the list.
     */
    private static ContentValues[] getShowsAsContentValues(List<ShowSearchMapper> shows) {
        ContentValues[] contentValues = new ContentValues[shows.size()];
        int i = 0;
        for (ShowSearchMapper show : shows) {
            contentValues[i] = show.toContentValues();
            i++;
        }
        return contentValues;
    }

    /**
     * Method that retrieves all the shows available in the TV Maze REST API.
     * @return array of ContentValues with all the shows.
     */
    public static ContentValues[] getAllTvShows() {
        initRestTemplate();
        boolean moreResultsAvailable = true;
        List<ShowSearchMapper> showsFound = new ArrayList<>();
        int page = 1;
        Log.i(TAG, "Starting the search for all the Tv Shows in TVMaze REST API");

        while (moreResultsAvailable) {
            try {
                String query = "page=" + String.valueOf(page);
                String Url = buildStringUrl(PATH_SHOWS, query);
                ShowSearchMapper[] shows = mRestTemplate.getForObject(Url, ShowSearchMapper[].class);
                showsFound.addAll(Arrays.asList(shows));
                page++;
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                    moreResultsAvailable = false;
                } else {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        Log.i(TAG, "Number of shows found: " + showsFound.size());
        return getShowsAsContentValues(showsFound);
    }

    public static List<ShowSearchMapper> getAllTvShowsByPage(int page) {
        if (page <= 0) {
            throw new IllegalArgumentException("Page number has to be a positive value");
        }

        initRestTemplate();
        boolean moreResultsAvailable = true;
        List<ShowSearchMapper> showsFound = new ArrayList<>();
        Log.i(TAG, "Starting the search for all the Tv Shows in TVMaze REST API");

        while (moreResultsAvailable) {
            try {
                String query = "page=" + page;
                String Url = buildStringUrl(PATH_SHOWS, query);
                ShowSearchMapper[] shows = mRestTemplate.getForObject(Url, ShowSearchMapper[].class);
                showsFound.addAll(Arrays.asList(shows));
                page++;
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                    moreResultsAvailable = false;
                } else {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        Log.i(TAG, "Number of shows found: " + showsFound.size());
        return showsFound;
    }
}
