package net.dereva.taxi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Order implements Parcelable {

    private long id;
    private OrderAddress startAddress;
    private OrderAddress endAddress;
    private Price price;
    private Date orderTime;
    private Vehicle vehicle;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderAddress getStartAddress() {
        return startAddress;
    }

    public OrderAddress getEndAddress() {
        return endAddress;
    }

    public Price getPrice() {
        return price;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    protected Order(Parcel in) {
        id = in.readLong();
        startAddress = (OrderAddress) in.readValue(OrderAddress.class.getClassLoader());
        endAddress = (OrderAddress) in.readValue(OrderAddress.class.getClassLoader());
        price = (Price) in.readValue(Price.class.getClassLoader());
        orderTime = (Date) in.readValue(Date.class.getClassLoader());
        vehicle = (Vehicle) in.readValue(Vehicle.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeValue(startAddress);
        dest.writeValue(endAddress);
        dest.writeValue(price);
        dest.writeValue(orderTime);
        dest.writeValue(vehicle);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };


}
