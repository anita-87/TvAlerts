package com.tvalerts.enums;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by anita on 4/02/16.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status implements Parcelable {
    RUNNING(1,"Running"), ENDED(2, "Ended"), IN_DEVELOPMENT(3, "In Development"), TO_BE_DETERMINED(4, "To Be Determined");


    private Integer id;
    private String name;

    private Status(final Integer id, final String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
    }

    public static final Parcelable.Creator<Status> CREATOR =
            new Parcelable.Creator<Status>(){

            public Status createFromParcel(Parcel in){
                return Status.values()[in.readInt()];
            }

            public Status[] newArray(int size){
                return new Status[size];
            }
    };
}
