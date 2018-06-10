package com.tvalerts.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

/**
 * Mapper for a character in a Tv Show.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Character {
    /**
     * The identifier of the character in the TvMaze REST API.
     */
    @Getter
    @Setter
    int id;
    /**
     * The name of the character in the Tv Show.
     */
    @Getter
    @Setter
    String name;
    /**
     * Image information for the character.
     */
    @Getter
    @Setter
    Image image;

    /**
     * Default constructor for the Character class.
     */
    public Character() {
    }

    /**
     * Constructor for the Character class with three parameters.
     *
     * @param id    - identifier of the character in the TvMaze REST API.
     * @param name  - name of the character in the Tv Show.
     * @param image - image information for the character.
     */
    public Character(int id, String name, Image image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
