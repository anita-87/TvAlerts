package com.tvalerts.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Class that holds the contract for the TvShow that will be stored in the database.
 *
 * This only stores some of the show information, not all of it. If the user wants details for
 * an individual show it will be requested at the moment, via a REST API request;
 */

public final class TvShowContract {

    /**
     * Constant value for the Authority for the Content Provider.
     */
    public static final String AUTHORITY = "com.tvalerts";
    /**
     * Constant value for the base content Uri for the Content Provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    /**
     * Constant value for a path to the Tv shows for the Content Provider.
     */
    public static final String PATH_SHOWS = "shows";

    /**
     * Private constructor in order to prevent that the contract class is instantiated.
     */
    private TvShowContract() {}

    /**
     * Inner class that defines the table content
     */
    public static class TvShowEntry implements BaseColumns {
        /**
         * TvShowEntry content URI = BASE_CONTENT_URI + PATH_SHOWS
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_SHOWS).build();
        /**
         * Constant for the name of the table in the database.
         */
        public static final String TABLE_NAME = "tvShow";
        /**
         * Constant for the name of the column that stores the show id in the REST API.
         */
        public static final String COLUMN_SHOW_ID = "showId";
        /**
         * Constant for the name of the column that stores the name of the show.
         */
        public static final String COLUMN_SHOW_NAME = "name";
        /**
         * Constant for the name of the column that stores the URL of the show in the REST API.
         */
        public static final String COLUMN_SHOW_URL = "url";
        /**
         * Constant for the name of the column that stores the image URL of the show.
         */
        public static final String COLUMN_SHOW_IMAGE = "image";
    }
}
