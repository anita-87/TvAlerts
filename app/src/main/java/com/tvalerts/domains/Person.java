package com.tvalerts.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Mapper for the person that plays a character in a Tv Show.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Person {

    /**
     * The identifier of the person in the TvMaze REST API.
     */
    @Getter
    @Setter
    int id;
    /**
     * The name of the person.
     */
    @Getter
    @Setter
    String name;
    /**
     * The country the person was born in.
     */
    @Getter
    @Setter
    Country country;
    /**
     * The date of birth of the person.
     */
    @Getter
    @Setter
    Date birthDate;
    /**
     * The death date of the person.
     */
    @Getter
    @Setter
    Date deathDate;
    /**
     * The gender of the person.
     */
    @Getter
    @Setter
    String gender;
    /**
     * Image information for the person.
     */
    @Getter
    @Setter
    Image image;

    /**
     * Default constructor for the Person class.
     */
    public Person() {
    }

    /**
     * Constructor for the Show class with seven parameters.
     *
     * @param id        - identifier of the person in the TvMaze REST API.
     * @param name      - name of the person.
     * @param country   - country the person was born in.
     * @param birthDate - date of birth of the person.
     * @param deathDate - death date of the person.
     * @param gender    - gender of the person.
     * @param image     - image information for the person.
     */
    public Person(int id, String name, Country country, Date birthDate,
                  Date deathDate, String gender, Image image) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.gender = gender;
        this.image = image;
    }
}
