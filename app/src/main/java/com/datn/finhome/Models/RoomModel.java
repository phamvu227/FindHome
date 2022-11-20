package com.datn.finhome.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.RequestCreator;

public class RoomModel  implements Parcelable {
    String address;
    String amount;
    String idHost;
    String idRoom;
    String image;
    String name;
    String price;

    public RoomModel(String address, String amount, String idHost, String idRoom, String image, String name, String price) {
        this.address = address;
        this.amount = amount;
        this.idHost = idHost;
        this.idRoom = idRoom;
        this.image = image;
        this.name = name;
        this.price = price;
    }

    RequestCreator compressionImageFit;
    private DatabaseReference nodeRoot;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIdHost() {
        return idHost;
    }

    public void setIdHost(String idHost) {
        this.idHost = idHost;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public RequestCreator getCompressionImageFit() {
        return compressionImageFit;
    }

    public void setCompressionImageFit(RequestCreator compressionImageFit) {
        this.compressionImageFit = compressionImageFit;
    }

    public DatabaseReference getNodeRoot() {
        return nodeRoot;
    }

    public void setNodeRoot(DatabaseReference nodeRoot) {
        this.nodeRoot = nodeRoot;
    }

    protected RoomModel(Parcel in) {
        address = in.readString();
        amount = in.readString();
        idHost = in.readString();
        idRoom = in.readString();
        image = in.readString();
        name = in.readString();
        price = in.readString();
    }

    public static final Creator<RoomModel> CREATOR = new Creator<RoomModel>() {
        @Override
        public RoomModel createFromParcel(Parcel in) {
            return new RoomModel(in);
        }

        @Override
        public RoomModel[] newArray(int size) {
            return new RoomModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(amount);
        dest.writeString(idHost);
        dest.writeString(idRoom);
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(price);
    }
}
