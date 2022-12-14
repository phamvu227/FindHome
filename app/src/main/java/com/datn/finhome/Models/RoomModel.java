package com.datn.finhome.Models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RoomModel implements Serializable {
    String title;
    String address;
    String description;
    Long idHost;
    String img;
    String name;
    Long price;
    String sizeRoom;

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

    public Map<String, Object> toMap() {
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

