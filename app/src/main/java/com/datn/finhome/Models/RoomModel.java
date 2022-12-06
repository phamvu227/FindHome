package com.datn.finhome.Models;

import java.io.Serializable;

public class RoomModel implements Serializable {
    String id;
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

    public RoomModel(String id, String address, String description, Long idHost, String img, String name, Long price, String sizeRoom) {
        this.id = id;
        this.address = address;
        this.description = description;
        this.idHost = idHost;
        this.img = img;
        this.name = name;
        this.price = price;
        this.sizeRoom = sizeRoom;
    }

    public RoomModel() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}

