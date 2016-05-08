package com.tvalerts.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.jsoup.Jsoup;

/**
 * Created by anita on 18/02/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Episode implements Parcelable {

    private int id;
    private String show;
    private String name;
    private String season;
    private String number;
    private String airdate;
    private String airtime;
    private String summary;

    public Episode(){}

    public Episode(Parcel in){
        readFromParcel(in);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAirdate() {
        return airdate;
    }

    public void setAirdate(String airdate) {
        this.airdate = airdate;
    }

    public String getAirtime() {
        return airtime;
    }

    public void setAirtime(String airtime) {
        this.airtime = airtime;
    }

    public String getSummary() {
        return Jsoup.parse(summary).text();
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getId());
        dest.writeString(this.getShow());
        dest.writeString(this.getName());
        dest.writeString(this.getSeason());
        dest.writeString(this.getNumber());
        dest.writeString(this.getAirdate());
        dest.writeString(this.getAirtime());
        dest.writeString(this.getSummary());
    }

    private void readFromParcel(Parcel in){
        this.id = in.readInt();
        this.show = in.readString();
        this.name = in.readString();
        this.season = in.readString();
        this.number = in.readString();
        this.airdate = in.readString();
        this.airtime = in.readString();
        this.summary = in.readString();
    }

    private static final Parcelable.Creator<Episode> CREATOR = new Parcelable.Creator<Episode>(){

        public Episode createFromParcel(Parcel in){
            return new Episode(in);
        }

        public Episode[] newArray(int size){
            return new Episode[size];
        }
    };
}
