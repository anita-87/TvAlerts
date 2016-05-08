package com.tvalerts.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Anita on 23/01/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Links implements Parcelable {

    private Link self;
    private Link previousepisode;
    private Link nextepisode;

    public Links(){}

    public Links(Parcel in){
        this.readFromParcel(in);
    }

    public Link getSelf() {
        return self;
    }

    public void setSelf(Link self) {
        this.self = self;
    }

    public Link getPreviousepisode() {
        return previousepisode;
    }

    public void setPreviousepisode(Link previousepisode) {
        this.previousepisode = previousepisode;
    }

    public Link getNextepisode() {
        return nextepisode;
    }

    public void setNextepisode(Link nextepisode) {
        this.nextepisode = nextepisode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.getSelf(), flags);
        dest.writeParcelable(this.getPreviousepisode(), flags);
        dest.writeParcelable(this.getNextepisode(), flags);
    }

    private void readFromParcel(Parcel in){
        this.self = in.readParcelable(Link.class.getClassLoader());
        this.previousepisode = in.readParcelable(Link.class.getClassLoader());
        this.nextepisode = in.readParcelable(Link.class.getClassLoader());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Links>(){

        public Links createFromParcel(Parcel in){
            return  new Links(in);
        }

        public Links[] newArray(int size){
            return new Links[size];
        }
    };

}
