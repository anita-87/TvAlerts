package com.tvalerts.mappers;

import android.content.ContentValues;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tvalerts.data.TvShowContract.TvShowEntry;

import lombok.Getter;
import lombok.Setter;

/**
 * Mapper class for the response of a Tv Show search in the TvMaze REST API.
 * This maps the shows performed via the "shows" path.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShowMapper {

    /**
     * The identification of the Tv show in the TvMaze REST API.
     */
    @Getter @Setter private String id;
    /**
     * The URL of the show in the TvMaze REST API.
     */
    @Getter @Setter private String url;
    /**
     * The name of the Tv show.
     */
    @Getter @Setter private String name;
    /**
     * The image object of the Tv show.
     */
    @Getter @Setter private ImageMapper image;
    /**
     * The status of the Tv show.
     * For example, Running, Ended, etc.
     */
    @Getter @Setter private String status;
    /**
     * Information about the network that broadcast the Tv show.
     */
    @Getter @Setter private Network network;
    /**
     * Converts the class to ContentValues type
     * @return The representation of the object as a ContentValues instance.
     */
    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TvShowEntry.COLUMN_SHOW_ID, id);
        contentValues.put(TvShowEntry.COLUMN_SHOW_URL, url);
        contentValues.put(TvShowEntry.COLUMN_SHOW_NAME, name);
        if (image != null) {
            contentValues.put(TvShowEntry.COLUMN_SHOW_IMAGE, image.getMedium());
        } else {
            contentValues.put(TvShowEntry.COLUMN_SHOW_IMAGE, "");
        }
        return contentValues;
    }
}
