package com.tvalerts.domains;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

/**
 * Mapper class for the Cast of the Tv Show.
 * It holds information about the person and the character that he/she plays.
 */
@Parcel
public class Cast {
    /**
     * Instance of the person that plays a character in a Tv Show.
     */
    @Getter
    @Setter
    Person person;
    /**
     * Instance of the character of a Tv Show.
     */
    @Getter
    @Setter
    Character character;

    /**
     * Default constructor for the Cast class.
     */
    public Cast() {
    }

    /**
     * Constructor for the Cast class with two parameters.
     *
     * @param person    - the person that is playing the character in the Tv Show.
     * @param character - the character being played in the Tv Show.
     */
    public Cast(Person person, Character character) {
        this.person = person;
        this.character = character;
    }
}
