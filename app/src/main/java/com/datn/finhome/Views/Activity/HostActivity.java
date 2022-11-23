package com.datn.finhome.Views.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.datn.finhome.Models.UserModel;
import com.datn.finhome.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HostActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private String userId;

    TextView  tvName,tvPhone;
    ImageView imgUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        tvName = findViewById(R.id.tvNameUser);
        tvPhone = findViewById(R.id.tvSdtUser);
        imgUser = findViewById(R.id.imgUser);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userId = firebaseUser.getUid();
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                if(userModel != null){
                    String fullName = userModel.name;
                    String Phone = userModel.phoneNumber;
                    String avatar = userModel.avatar;

                    tvName.setText(fullName);
                    tvPhone.setText(Phone);
                    Glide.with(getApplicationContext()).load(avatar).into(imgUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HostActivity.this, "That bai", Toast.LENGTH_SHORT).show();
            }
        });

    }
}