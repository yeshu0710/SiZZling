package com.example.yeshu.sizzling.JsonData;
/**
 * Created by Yeshu on 07-06-2018.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class ReceipeJsonDate implements Parcelable {

    private int id;
    private String name;
    private String ingredients;
    private String steps;
    public ReceipeJsonDate(int id, String name, String ingredients, String steps) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    private ReceipeJsonDate(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.readString();
        steps = in.readString();
    }

    public static final Creator<ReceipeJsonDate> CREATOR = new Creator<ReceipeJsonDate>() {
        @Override
        public ReceipeJsonDate createFromParcel(Parcel in) {
            return new ReceipeJsonDate(in);
        }

        @Override
        public ReceipeJsonDate[] newArray(int size) {
            return new ReceipeJsonDate[size];
        }
    };

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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(ingredients);
        parcel.writeString(steps);
    }
}
