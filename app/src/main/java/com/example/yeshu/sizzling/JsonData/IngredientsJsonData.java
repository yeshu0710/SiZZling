package com.example.yeshu.sizzling.JsonData;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * Created by Yeshu on 11-06-2018.
 */

public class IngredientsJsonData implements Parcelable {
    private String quantity;
    private String measurement;
    private String ingredients;

    private IngredientsJsonData(Parcel in) {
        quantity = in.readString();
        measurement = in.readString();
        ingredients = in.readString();
    }


    public static final Creator<IngredientsJsonData> CREATOR = new Creator<IngredientsJsonData>() {
        @Override
        public IngredientsJsonData createFromParcel(Parcel in) {
            return new IngredientsJsonData(in);
        }

        @Override
        public IngredientsJsonData[] newArray(int size) {
            return new IngredientsJsonData[size];
        }
    };

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public IngredientsJsonData(String quantity, String measurement, String ingredients) {
        this.quantity = quantity;
        this.measurement = measurement;
        this.ingredients = ingredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(quantity);
        parcel.writeString(measurement);
        parcel.writeString(ingredients);
    }
}
