package com.tvalerts.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

/**
 * Mapper class for the response of a Tv Show search in the TvMaze REST API.
 * This maps the shows performed via the "search" path.
 *
 * Uses the Parcel annotation to use the Parceler library to parcel the class.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class ShowSearch {

    /**
     * Represents the order of relevancy or closeness of the search with what the user was trying to find.
     */
    @Getter @Setter String score;

    /**
     * The actual show information.
     */
    @Getter @Setter Show show;

    /**
     * Default constructor for the ShowSearch class.
     */
    public ShowSearch() {}

    /**
     * Constructor for the ShowSearch class with two parameters.
     * @param score - represents to closenes of the show to be the one searched by the user.
     * @param show - the actual show instance.
     */
    public ShowSearch(String score, Show show) {
        this.score = score;
        this.show = show;
    }
}
