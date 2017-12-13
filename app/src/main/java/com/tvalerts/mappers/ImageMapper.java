package com.tvalerts.mappers;

/**
 * Mapper class for the response of a Tv Show image object in the TvMaze REST API.
 */

public class ImageMapper {
    /**
     * Url to the medium quality image of the Tv show.
     */
    private String medium;
    /**
     * Url to the original quality image of the Tv show.
     */
    private String original;

    /**
     * Getter method for the property medium
     * @return The Url to the medium quality image of the Tv show.
     */
    public String getMedium() {
        return medium;
    }

    /**
     * Setter method for the property medium
     * @param medium The Url to the medium quality image of the Tv show.
     */
    public void setMedium(String medium) {
        this.medium = medium;
    }

    /**
     * Getter method for the property original
     * @return The Url to the original quality image of the Tv show.
     */
    public String getOriginal() {
        return original;
    }

    /**
     * Setter method for the property original
     * @param original The Url to the original quality image of the Tv show.
     */
    public void setOriginal(String original) {
        this.original = original;
    }
}
