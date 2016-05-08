package com.tvalerts.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by anita on 12/02/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person implements Parcelable{

    private int id;
    private String url;
    private String name;
    private Image image;

    public Person(){}

    public Person(Parcel in){
        readFromParcel(in);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getId());
        dest.writeString(this.getUrl());
        dest.writeString(this.getName());
        dest.writeParcelable(this.getImage(), flags);
    }

    @Override
    public String toString(){
        return this.getName();
    }

    private void readFromParcel(Parcel in){
        this.id = in.readInt();
        this.url = in.readString();
        this.name = in.readString();
        this.image = in.readParcelable(Image.class.getClassLoader());
    }

    private static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>(){

        public Person createFromParcel(Parcel in){
            return new Person(in);
        }

        public Person[] newArray(int size){
            return new Person[size];
        }
    };
}
