package com.tvalerts.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tvalerts.enums.Status;

import org.jsoup.Jsoup;

/**
 * Created by Anita on 23/01/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Show implements Parcelable{

    private static final String PARAGRAPH_START_TAG = "<p>";
    private static final String PARAGRAPH_END_TAG = "</p>";

    private int id;
    private String name;
    private Status status;
    private Network network;
    private Schedule schedule;
    private Image image;
    private String summary;
    private Links _links;

    public Show(){

    }

    public Show(Parcel in){
        readFromParcel(in);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getSummary() {
        return Jsoup.parse(summary).text();
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getId());
        dest.writeString(this.getName());
        dest.writeParcelable(this.getStatus(), flags);
        dest.writeParcelable(this.getNetwork(), flags);
        dest.writeParcelable(this.getSchedule(), flags);
        dest.writeParcelable(this.getImage(), flags);
        dest.writeString(this.getSummary());
        dest.writeParcelable(this.get_links(), flags);
    }

    private void readFromParcel(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.status = in.readParcelable(Status.class.getClassLoader());
        this.network = in.readParcelable(Network.class.getClassLoader());
        this.schedule = in.readParcelable(Schedule.class.getClassLoader());
        this.image = in.readParcelable(Image.class.getClassLoader());
        this.summary = in.readString();
        this._links = in.readParcelable(Links.class.getClassLoader());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Show>(){

        public Show createFromParcel(Parcel in){
            return new Show(in);
        }

        public Show[] newArray(int size){
            return new Show[size];
        }
    };

    public String getSummaryNoFormat(){
        return this.getSummary().replace(PARAGRAPH_START_TAG, "").replace(PARAGRAPH_END_TAG, "");
    }
}
