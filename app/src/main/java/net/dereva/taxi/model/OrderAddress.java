package net.dereva.taxi.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderAddress implements Parcelable {

    private String city;
    private String address;

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    protected OrderAddress(Parcel in) {
        city = in.readString();
        address = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(address);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OrderAddress> CREATOR = new Parcelable.Creator<OrderAddress>() {
        @Override
        public OrderAddress createFromParcel(Parcel in) {
            return new OrderAddress(in);
        }

        @Override
        public OrderAddress[] newArray(int size) {
            return new OrderAddress[size];
        }
    };
}
