package com.tvalerts.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;
/**
 * Mapper class for the Network of a Tv Show from the REST API.
 * This will be used in the ShowMapper class.
 *
 * Uses the Parcel annotation to use the Parceler library to parcel the class.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Network {
    /**
     * The identification of the Network in the TvMaze REST API.
     */
    @Getter @Setter Integer id;
    /**
     * The Name of the Network the Tv Show is being broadcasted on.
     */
    @Getter @Setter String name;
    /**
     * The Country of the Network the Tv Show is being broadcasted on.
     */
    @Getter @Setter Country country;

    /**
     * Default constructor for the Network class.
     */
    public Network() {}

    /**
     * Constructor for the Network class with three parameters.
     * @param id - the identifier of the Tv network.
     * @param name - the name of the Tv network.
     * @param country - the country of the Tv network.
     */
    public Network(Integer id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    @Override
    public String toString() {
        return name + " (" + country.getName() + ")";
    }
}
