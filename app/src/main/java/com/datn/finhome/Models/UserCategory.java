package com.datn.finhome.Models;

import android.media.Image;

import java.util.List;

public class UserCategory {
    private UserModel user;
    private List<Image> images;

    public UserCategory(UserModel user, List<Image> images) {
        this.user = user;
        this.images = images;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
