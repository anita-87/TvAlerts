package com.tvalerts.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Mapper class for the response of a Tv Show search in the TvMaze REST API.
 * This maps the shows performed via the "search" path.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShowSearch {

    /**
     * Represents the order of relevancy or closeness of the search with what the user was trying to find.
     */
    @Getter @Setter private String score;
    /**
     * The actual show information.
     */
    @Getter @Setter private Show show;

}
