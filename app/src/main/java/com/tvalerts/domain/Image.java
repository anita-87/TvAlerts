package com.tvalerts.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anita on 4/02/16.
 */
public class Image implements Parcelable {

    private String medium;
    private String original;

    public Image(){}

    public Image(Parcel in){
        this.readFromParcel(in);
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getMedium());
        dest.writeString(this.getOriginal());
    }

    private void readFromParcel(Parcel in){
        this.medium = in.readString();
        this.original = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>(){

        public Image createFromParcel(Parcel in){
            return new Image(in);
        }

        public Image[] newArray(int size){
            return new Image[size];
        }
    };
}
