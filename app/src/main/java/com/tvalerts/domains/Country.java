package com.tvalerts.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

/**
 * Mapper class for the Country of a Tv Network from the REST API.
 *
 * Uses the Parcel annotation to use the Parceler library to parcel the class.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Country {

    /**
     * The name of the country as a string.
     */
    @Getter @Setter String name;

    /**
     * Default constructor for the Network class.
     */
    public Country() {}

    /**
     * Constructor for the Country class with one parameter.
     * @param name - the name of the country as a string.
     */
    public Country(String name) {
        this.name = name;
    }
}
