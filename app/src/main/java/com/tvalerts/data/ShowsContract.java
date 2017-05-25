package com.tvalerts.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by anita on 10/05/17.
 */

public class ShowsContract {

    // Content Authority is a name for the entire content provider.
    public static final String CONTENT_AUTHORITY = "com.tvalerts";

    // Base of all the URI's which apps will use to contact the content provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths that can be appended to BASE_CONTENT_URI to form valid URI's that the app can handle
    public static final String PATH_SHOWS = "shows";

    public static final class ShowEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_SHOWS)
                .build();

        public static final String TABLE_NAME = "show";
        public static final String COLUMN_SHOW_API_ID = "id";
        public static final String COLUMN_SHOW_NAME = "name";
        public static final String COLUMN_SHOW_URL = "url";
        public static final String COLUMN_SHOW_TYPE = "type";
        public static final String COLUMN_SHOW_LANGUAGE = "language";
        public static final String COLUMN_SHOW_STATUS = "status";
        public static final String COLUMN_SHOW_PREMIERED = "premiered";
    }

}
