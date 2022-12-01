package com.datn.finhome.Models;

public class HostModel {
    String address;
    String avatar;
    Long id;
    String name;
    Long number_phone;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumber_phone() {
        return number_phone;
    }

    public void setNumber_phone(Long number_phone) {
        this.number_phone = number_phone;
    }

    public HostModel() {
    }

    public HostModel(String address, String avatar, Long id, String name, Long number_phone) {
        this.address = address;
        this.avatar = avatar;
        this.id = id;
        this.name = name;
        this.number_phone = number_phone;
    }
}

