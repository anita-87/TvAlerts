package com.tvalerts.mappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
/**
 * Mapper class for the Network of a Tv Show from the REST API.
 * This will be used in the ShowMapper class.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Network {
    /**
     * The identification of the Network in the TvMaze REST API.
     */
    @Getter @Setter private Integer id;
    /**
     * The Name of the Network the Tv Show is being broadcasted on.
     */
    @Getter @Setter private String name;
}
