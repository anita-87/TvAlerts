package com.tvalerts.domains;

import android.text.TextUtils;

import org.parceler.Parcel;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;

@Parcel
public class Schedule {

    private static final String[] LABOUR_WEEK_DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday"};
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

    @Override
    public String toString() {
        String schedule;
        switch (days.length) {
            case 1:
                schedule = days[0] + " at " + time;
                break;
            case 5:
                if (Arrays.equals(days, LABOUR_WEEK_DAYS)) {
                    schedule = "Monday through Friday at " + time;
                    break;
                } else {
                    schedule = TextUtils.join(", ", days) + " at " + time;
                    break;
                }
            case 7:
                schedule = "Weekly at " + time;
                break;
            case 2:
            case 3:
            case 4:
            case 6:
                schedule = TextUtils.join(", ", days) + " at " + time;
                break;
            default:
                schedule = TextUtils.join(", ", days) + " at " + time;
                break;
        }
        return schedule;
    }
}
