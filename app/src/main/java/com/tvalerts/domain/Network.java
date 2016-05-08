package com.tvalerts.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by anita on 4/02/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Network implements Parcelable {

    private String name;
    private Country country;

    public Network(){}

    public Network(Parcel in){
        this.readFromParcel(in);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getName());
        dest.writeParcelable(this.getCountry(), flags);
    }

    private void readFromParcel(Parcel in){
        this.name = in.readString();
        this.country = in.readParcelable(Country.class.getClassLoader());
    }

    public static final Parcelable.Creator<Network> CREATOR = new Parcelable.Creator<Network>(){

        public Network createFromParcel(Parcel in){
            return new Network(in);
        }

        public Network[] newArray(int size){
            return new Network[size];
        }

    };
}
