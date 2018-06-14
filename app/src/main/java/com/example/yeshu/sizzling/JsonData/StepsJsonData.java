package com.example.yeshu.sizzling.JsonData;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * Created by Yeshu on 11-06-2018.
 */

public class StepsJsonData implements Parcelable {
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    private StepsJsonData(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }


    public static final Creator<StepsJsonData> CREATOR = new Creator<StepsJsonData>() {
        @Override
        public StepsJsonData createFromParcel(Parcel in) {
            return new StepsJsonData(in);
        }

        @Override
        public StepsJsonData[] newArray(int size) {
            return new StepsJsonData[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public StepsJsonData(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);
    }
}
