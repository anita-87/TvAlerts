package com.tvalerts.mappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Mapper class for the response of a Tv Show search in the TvMaze REST API.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShowSearchMapper {

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
     * The URL of the medium size image for this Tv show.
     */
    private String mediumImage;
    /**
     * The URL of the original size image for this Tv show.
     */
    private String originalImage;

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
     * Getter method for the property mediumImage
     * @return The string URL of the medium quality image for the Tv show.
     */
    public String getMediumImage() {
        return mediumImage;
    }

    /**
     * Setter method for the property mediumImage
     * @param mediumImage The string URL of the medium quality image for the Tv show.
     */
    public void setMediumImage(String mediumImage) {
        this.mediumImage = mediumImage;
    }

    /**
     * Getter method for the property originalImage
     * @return The string URL of the original quality image for the Tv show.
     */
    public String getOriginalImage() {
        return originalImage;
    }

    /**
     * Setter method for the property originalImage
     * @param originalImage The string URL of the original quality image for the Tv show.
     */
    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }
}
