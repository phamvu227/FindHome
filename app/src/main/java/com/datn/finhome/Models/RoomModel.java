package com.datn.finhome.Models;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import kotlin.jvm.internal.MagicApiIntrinsics;

public class RoomModel {
    String title;
    String address;
    String sizeRoom;
    Integer price;
    String description;
//    File image;

    public RoomModel(){

    }

    public RoomModel(String title, String address, String sizeRoom, Integer price, String description) {
        this.title = title;
        this.address = address;
        this.sizeRoom = sizeRoom;
        this.price = price;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSizeRoom() {
        return sizeRoom;
    }

    public void setSizeRoom(String sizeRoom) {
        this.sizeRoom = sizeRoom;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public File getImage() {
//        return image;
//    }
//
//    public void setImage(File image) {
//        this.image = image;
//    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("address", address);
        result.put("name", title);
//        result.put("img", image);
        result.put("price", price);
        result.put("description", description);
        result.put("sizeRoom", sizeRoom);
        return result;
    }
}
