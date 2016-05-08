package com.tvalerts.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Anita on 23/01/16.
 */
public class Link implements Parcelable {

    private String href;

    public Link(){}

    public Link(Parcel in){
        this.readFromParcel(in);
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getHref());
    }

    private void readFromParcel(Parcel in){
        this.href = in.readString();
    }

    public static final Parcelable.Creator<Link> CREATOR = new Parcelable.Creator<Link>(){

        public Link createFromParcel(Parcel in){
            return new Link(in);
        }

        public Link[] newArray(int size){
            return new Link[size];
        }

    };
}