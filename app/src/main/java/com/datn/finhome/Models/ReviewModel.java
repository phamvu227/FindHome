package com.datn.finhome.Models;

public class ReviewModel {
    String idUser;
    String reviews;
    Long idRoom;

    public ReviewModel(String idUser, String reviews, Long idRoom) {
        this.idUser = idUser;
        this.reviews = reviews;
        this.idRoom = idRoom;
    }

    public ReviewModel() {
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public Long getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Long idRoom) {
        this.idRoom = idRoom;
    }
}
