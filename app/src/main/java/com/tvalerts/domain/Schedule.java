package com.tvalerts.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anita on 4/02/16.
 */
public class Schedule implements Parcelable {

    private String time;
    private String[] days;

    public Schedule(){}

    public Schedule(Parcel in){
        this.readFromParcel(in);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String[] getDays() {
        return days;
    }

    public void setDays(String[] days) {
        this.days = days;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getTime());
        dest.writeStringArray(this.getDays());
    }

    @Override
    public String toString(){
        String scheduleString = "";

        if (this.getDays().length == 0)
            return "-";

        if (this.getDays().length > 1){
            //Show is on more than one day a week
            scheduleString = this.getDays()[0] + " to " + this.getDays()[this.getDays().length - 1] + " at " + this.getTime();

        } else {
            //Show is on one day a week
            scheduleString = days[0] + " at " + this.getTime();
        }

        return scheduleString;
    }

    private void readFromParcel(Parcel in){
        this.time = in.readString();
        this.days = in.createStringArray();
    }

    public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>(){
        public Schedule createFromParcel(Parcel in){
            return new Schedule(in);
        }

        public Schedule[] newArray(int size){
            return new Schedule[size];
        }
    };
}
