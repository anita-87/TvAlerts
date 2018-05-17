package com.tvalerts.mappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Mapper class for the response of a Tv Show search in the TvMaze REST API.
 * This maps the shows performed via the "search" path.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShowSearchMapper {

    /**
     * Represents the order of relevancy or closeness of the search with what the user was trying to find.
     */
    private String score;
    /**
     * The actual show infromation.
     */
    private ShowMapper show;

    /**
     * Getter method for the property score
     * @return the score of the Tv show.
     */
    public String getScore() {
        return score;
    }

    /**
     * Setter method for the property score
     * @param score The score of the Tv show.
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * Getter method for the property show
     * @return the actual show.
     */
    public ShowMapper getShow() {
        return show;
    }

    /**
     * Setter method for the property show
     * @param show The actual show.
     */
    public void setShow(ShowMapper show) {
        this.show = show;
    }
}
