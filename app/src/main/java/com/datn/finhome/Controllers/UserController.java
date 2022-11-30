package com.datn.finhome.Controllers;

import android.content.Context;

import com.datn.finhome.IAfterGetAllObject;
import com.datn.finhome.Models.UserModel;
import com.google.firebase.database.FirebaseDatabase;

public class UserController {
    Context context;
    UserModel userModel;

    public UserController(Context context) {
        this.context = context;
        this.userModel = new UserModel();
    }

    public void addUser(UserModel newUserModel, String uid) {
        userModel.addUser(newUserModel, uid);
    }


}
