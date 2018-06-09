package com.tvalerts.domains;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

@Parcel
public class Schedule {
    /**
     * The time of the day the show is broadcasted.
     */
    @Getter @Setter String time;
    /**
     * The days of the week the show is broadcasted.
     */
    @Getter @Setter String[] days;

    /**
     * Default constructor for the Schedule class.
     */
    public Schedule() {}

    /**
     * Constructor for the Schedule class with two parameters.
     * @param time - the time of the day the show is broadcasted.
     * @param days - the days of the week the show is broadcasted.
     */
    public Schedule(String time, String[] days) {
        this.time = time;
        this.days = days;
    }
}
