package com.tvalerts.network;

import android.util.Log;

import com.tvalerts.domains.Show;
import com.tvalerts.domains.ShowSearch;

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
     * Constant value for the path of search in the TV Maze REST API URL.
     */
    private static final String PATH_SEARCH = "search";
    /**
     * Template to create HTTP connections and handle errors.
     */
    private static RestTemplate mRestTemplate;

    /**
     * Method used to build the TV Maze URLs as string values.
     * @param paths The relative path in the URL to consult. This value cannot be null.
     * @param query The query value for the request, for example, "page=1". This value can be null.
     * @return a string representation of a URL in the TV Maze REST API.
     */
    private static String buildStringUrl(String query, String... paths) {
        UriComponents uriComponents;
        if (paths == null) {
            throw new IllegalArgumentException("Parameter path is mandatory");
        }
        if (query == null) {
            uriComponents = UriComponentsBuilder.newInstance()
                    .scheme(PROTOCOL).host(HOST).pathSegment(paths).build();
        } else {
            uriComponents = UriComponentsBuilder.newInstance()
                    .scheme(PROTOCOL).host(HOST).pathSegment(paths).query(query).build();
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
     * Methods that retrieves all the shows available in the TV Maze REST API by page.
     * @param page The page to query the REST API.
     * @return list of shows retrive from the REST API or null.
     */
    public static List<Show> getAllTvShowsByPage(int page) {
        if (page <= 0) {
            throw new IllegalArgumentException("Page number has to be a positive value");
        }

        initRestTemplate();
        List<Show> showsFound = new ArrayList<>();
        Log.i(TAG, "Starting the search for the Tv Shows in TVMaze REST API for page: " + page);

        try {
            String query = "page=" + page;
            String url = buildStringUrl(query, PATH_SHOWS);
            Show[] shows = mRestTemplate.getForObject(url, Show[].class);
            showsFound.addAll(Arrays.asList(shows));
            Log.i(TAG, "Number of shows found: " + showsFound.size());
            return showsFound;
        } catch (HttpClientErrorException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * Methods that retrieves all the shows available in the TV Maze REST API matching the query parameter passed.
     * @param query - The string used to perform a search against the REST API.
     * @return list of shows retrive from the REST API or null.
     */
    public static List<ShowSearch> queryTvShows(String query) {
        if (query.isEmpty()) {
            return null;
        } else {
            initRestTemplate();
            List<ShowSearch> showsFound = new ArrayList<>();
            Log.i(TAG, "Starting the search for the Tv Shows in TVMaze REST API starting by: " + query);
            try {
                String queryParameter = "q=" + query;
                String url = buildStringUrl(queryParameter, PATH_SEARCH, PATH_SHOWS);
                ShowSearch[] shows = mRestTemplate.getForObject(url, ShowSearch[].class);
                showsFound.addAll(Arrays.asList(shows));
                Log.i(TAG, "Number of shows found: " + showsFound.size());
                return showsFound;
            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }
    }
}
