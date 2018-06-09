package com.tvalerts.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

/**
 * Mapper class for the response of a Tv Show search in the TvMaze REST API.
 * This maps the shows performed via the "shows" path.
 *
 * Uses the Parcel annotation to use the Parceler library to parcel the class.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Show {

    /**
     * The identification of the Tv show in the TvMaze REST API.
     */
    @Getter @Setter String id;
    /**
     * The URL of the show in the TvMaze REST API.
     */
    @Getter @Setter String url;
    /**
     * The name of the Tv show.
     */
    @Getter @Setter String name;
    /**
     * The image object of the Tv show.
     */
    @Getter @Setter Image image;
    /**
     * The status of the Tv show.
     * For example, Running, Ended, etc.
     */
    @Getter @Setter String status;
    /**
     * Information about the network that broadcast the Tv show.
     */
    @Getter @Setter Network network;
    /**
     * The summary of the Tv Show.
     */
    @Getter @Setter String summary;

    /**
     * Default constructor for the Show class.
     */
    public Show() {}

    /**
     * Constructor for the Show class with seven parameters.
     * @param id - the identifier of the Tv Show.
     * @param url - the URL link of the show in the TvMaze API.
     * @param name - the name of the Tv show.
     * @param image - the image class that holds images of the Tv Show.
     * @param status - the status of the Tv Show.
     * @param network - the network class that holds information
     *                about the Tv network of the Tv Show.
     * @param summary - a summary of the Tv Show.
     */
    public Show(String id, String url, String name, Image image,
                String status, Network network, String summary) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.image = image;
        this.status = status;
        this.network = network;
        this.summary = summary;
    }
}
