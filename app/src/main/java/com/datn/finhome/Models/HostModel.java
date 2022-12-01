package com.datn.finhome.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.RequestCreator;

public class HostModel implements Parcelable {
    String name;
    String numberPhone;
    String address;
    String avatar;
    String hostID;

    RequestCreator compressionImageFit;
    private DatabaseReference nodeRoot;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
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

    public HostModel() {
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    protected HostModel(Parcel in) {
        name = in.readString();
        numberPhone = in.readString();
        address = in.readString();
        avatar = in.readString();
        hostID = in.readString();
    }

    public static final Creator<HostModel> CREATOR = new Creator<HostModel>() {
        @Override
        public HostModel createFromParcel(Parcel in) {
            return new HostModel(in);
        }

        @Override
        public HostModel[] newArray(int size) {
            return new HostModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(numberPhone);
        dest.writeString(address);
        dest.writeString(avatar);
    }
}
