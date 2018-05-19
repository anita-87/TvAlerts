package com.tvalerts.mappers;

import lombok.Getter;
import lombok.Setter;

/**
 * Mapper class for the response of a Tv Show image object in the TvMaze REST API.
 */

public class ImageMapper {
    /**
     * Url to the medium quality image of the Tv show.
     */
    @Getter @Setter private String medium;
    /**
     * Url to the original quality image of the Tv show.
     */
    @Getter @Setter private String original;

}
