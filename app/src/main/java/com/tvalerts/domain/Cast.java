package com.tvalerts.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by anita on 12/02/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cast implements Parcelable{

    private Person person;

    public Cast(){}

    public Cast(Parcel in){}

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.getPerson(), flags);
    }

    private void readFromParcel(Parcel in){
       this.person = in.readParcelable(Person.class.getClassLoader());
    }

    public static final Parcelable.Creator<Cast> CREATOR = new Parcelable.Creator<Cast>(){
        public Cast createFromParcel(Parcel in){
            return new Cast(in);
        }

        public Cast[] newArray(int size){
            return new Cast[size];
        }
    };
}
