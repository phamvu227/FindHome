package com.datn.finhome.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RoomModel implements Parcelable {
    String title;
    String address;
    String description;
    Long idHost;
    String img;
    String name;
    Long price;
    String sizeRoom;
    Long idRoom;

    String id;

    public RoomModel(String name, String address, String sizeRoom, Long price, String description, Long idHost) {
        this.name = name;
        this.address = address;
        this.sizeRoom = sizeRoom;
        this.price = price;
        this.description = description;
        this.idHost = idHost;
    }
    public RoomModel() {
    }

    protected RoomModel(Parcel in) {
        title = in.readString();
        address = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            idHost = null;
        } else {
            idHost = in.readLong();
        }
        img = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readLong();
        }
        sizeRoom = in.readString();
        id = in.readString();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getSizeRoom() {
        return sizeRoom;
    }

    public void setSizeRoom(String sizeRoom) {
        this.sizeRoom = sizeRoom;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIdHost() {
        return idHost;
    }

    public void setIdHost(Long idHost) {
        this.idHost = idHost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Long idRoom) {
        this.idRoom = idRoom;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("address", address);
        result.put("name", title);
//        result.put("img", image);
        result.put("price", price);
        result.put("description", description);
        result.put("sizeRoom", sizeRoom);
        result.put("idRoom", idRoom);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(address);
        parcel.writeString(description);
        if (idHost == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(idHost);
        }
        parcel.writeString(img);
        parcel.writeString(name);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(price);
        }
        parcel.writeString(sizeRoom);
        parcel.writeLong(idRoom);
        parcel.writeString(id);
    }
}

