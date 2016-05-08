package com.tvalerts.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by anita on 4/02/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country implements Parcelable{

    private String name;
    private String code;

    public Country(){

    }

    public Country(Parcel in){
        this.readFromParcel(in);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getName());
        dest.writeString(this.getCode());
    }

    private void readFromParcel(Parcel in){
        this.name = in.readString();
        this.code = in.readString();
    }

    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {

        public Country createFromParcel(Parcel in){
            return new Country(in);
        }

        public Country[] newArray(int size){
            return new Country[size];
        }

    };
}
