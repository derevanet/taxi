package net.dereva.taxi.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Vehicle implements Parcelable {

    private String regNumber;
    private String modelName;
    private String photo;
    private String driverName;

    public String getRegNumber() {
        return regNumber;
    }

    public String getModelName() {
        return modelName;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDriverName() {
        return driverName;
    }

    protected Vehicle(Parcel in) {
        regNumber = in.readString();
        modelName = in.readString();
        photo = in.readString();
        driverName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(regNumber);
        dest.writeString(modelName);
        dest.writeString(photo);
        dest.writeString(driverName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Vehicle> CREATOR = new Parcelable.Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };
}
