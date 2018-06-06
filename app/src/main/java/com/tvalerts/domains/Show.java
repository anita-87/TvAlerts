package com.tvalerts.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Mapper class for the response of a Tv Show search in the TvMaze REST API.
 * This maps the shows performed via the "shows" path.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Show {

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
    @Getter @Setter private Image image;
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
     * The summary of the Tv Show.
     */
    @Getter @Setter private String summary;
}
