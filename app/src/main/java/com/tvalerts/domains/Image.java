package com.tvalerts.domains;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

/**
 * Mapper class for the response of a image object in the TvMaze REST API.
 * Uses the Parcel annotation to use the Parceler library to parcel the class.
 */

@Parcel
public class Image {
    /**
     * Url to the medium quality image of the Tv show, person or character.
     */
    @Getter
    @Setter
    String medium;
    /**
     * Url to the original quality image of the Tv show, person or character.
     */
    @Getter
    @Setter
    String original;

    /**
     * Default constructor for the Image class.
     */
    public Image() {
    }

    /**
     * Constructor for the Image class with two parameters.
     *
     * @param medium   - string with the URL of the medium size image.
     * @param original - string with the URL of the original size image.
     */
    public Image(String medium, String original) {
        this.medium = medium;
        this.original = original;
    }
}
