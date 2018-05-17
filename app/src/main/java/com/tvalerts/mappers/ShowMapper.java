package com.tvalerts.mappers;

import android.content.ContentValues;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.tvalerts.data.TvShowContract;
import com.tvalerts.data.TvShowContract.*;

/**
 * Mapper class for the response of a Tv Show search in the TvMaze REST API.
 * This maps the shows performed via the "shows" path.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShowMapper {

    /**
     * The identification of the Tv show in the TvMaze REST API.
     */
    private String id;
    /**
     * The URL of the show in the TvMaze REST API.
     */
    private String url;
    /**
     * The name of the Tv show.
     */
    private String name;
    /**
     * The image object of the Tv show.
     */
    private ImageMapper image;
    /**
     * The status of the Tv show.
     * For example, Running, Ended, etc.
     */
    private String status;
    /**
     * Getter method for the property id
     * @return the id of the Tv show.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for the property id
     * @param id The id of the Tv show.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for the property url
     * @return The string url of the Tv show in the Tv Maze REST API.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter method for the property url
     * @param url The string url of the Tv show in the Tv Maze REST API.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Getter method for the property name
     * @return The name of the Tv show.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for the property name
     * @param name The name of the Tv show.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for the property image
     * @return Object that contains two references of images for the show.
     */
    public ImageMapper getImage() {
        return image;
    }

    /**
     * Setter method for the property image
     * @param image Object that contains two references of images for the show.
     */
    public void setImage(ImageMapper image) {
        this.image = image;
    }

    /**
     * Getter method for the property status
     * @return The status of the show.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for the property status
     * @param status The string that represents the status of the show.
     */
    public void setStatus(String status) {
        this.status = status;
    }

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
