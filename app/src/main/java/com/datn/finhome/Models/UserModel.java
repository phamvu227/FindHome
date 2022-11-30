package com.datn.finhome.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserModel implements Parcelable {
   public String avatar;
   public String name;
   public String email;
   public String phoneNumber;
    boolean owner, gender;
//    private List<RoomModel> list_room;
//
//    public List<RoomModel> getList_room() {
//        return list_room;
//    }

//    public void setList_room(List<RoomModel> list_room) {
//        this.list_room = list_room;
//    }

    //Id người dùng ở đây là uid trong firebaseauthen
    String userID;

    RequestCreator compressionImageFit;

    protected UserModel(Parcel in) {
        avatar = in.readString();
        name = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        owner = in.readByte() != 0;
        gender = in.readByte() != 0;
        userID = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        owner = owner;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public RequestCreator getCompressionImageFit() {
        return compressionImageFit;
    }

    public void setCompressionImageFit(RequestCreator compressionImageFit) {
        this.compressionImageFit = compressionImageFit;
    }

    //Biến lưu root của firebase, lưu ý để biến là private
    private DatabaseReference nodeRoot;

    //hàm khởi tạo rỗng
    public UserModel() {

        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatar);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeByte((byte) (owner ? 1 : 0));
        dest.writeByte((byte) (gender ? 1 : 0));
        dest.writeString(userID);
    }

    public void addUser(UserModel newUserModel, String uid) {
        DatabaseReference nodeUser = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        nodeUser.setValue(newUserModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                }
            }
        });
    }

//    public Map<String, Object> toMapListRoom() {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("list_room", list_room);
//        return map;
//    }

}
