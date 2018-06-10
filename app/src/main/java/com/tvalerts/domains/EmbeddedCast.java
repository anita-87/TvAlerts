package com.tvalerts.domains;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

/**
 * Mapper class for the Cast of the Tv Show that comes embedded in the Show information.
 * It holds an array of Cast objects.
 */
@Parcel
public class EmbeddedCast {
    /**
     * Array of Cast information for the Tv Show.
     */
    @Getter
    @Setter
    Cast[] cast;

    /**
     * Default constructor for the EmbeddedCast class.
     */
    public EmbeddedCast() {
    }

    /**
     * Constructor for the EmbeddedCast class with seven parameters.
     *
     * @param cast - the cast array for this object.
     */
    public EmbeddedCast(Cast[] cast) {
        this.cast = cast;
    }
}
