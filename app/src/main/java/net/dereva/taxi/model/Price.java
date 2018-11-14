package net.dereva.taxi.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Price implements Parcelable {

    private int amount;
    private String currency;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    protected Price(Parcel in) {
        amount = in.readInt();
        currency = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(amount);
        dest.writeString(currency);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Price> CREATOR = new Parcelable.Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel in) {
            return new Price(in);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };
}
