package com.datn.finhome.Views.Activity;

import static com.datn.finhome.Utils.Const.HOST_FIREBASE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.datn.finhome.Models.HostModel;
import com.datn.finhome.databinding.ActivityHostDetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HostDetailsActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ActivityHostDetailsBinding binding;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHostDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = mDatabase.child(HOST_FIREBASE);
        ValueEventListener valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HostModel hostModel = snapshot.getValue(HostModel.class);
                binding.tvNameHost.setText(hostModel.getName());
                binding.tvAddressHost.setText(hostModel.getAddress());
                binding.tvSdtHost.setText(hostModel.getNumberPhone());
                Glide.with(getApplicationContext())
                        .load(hostModel.getAvatar())
                        .into(binding.imgHost);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        usersRef.addListenerForSingleValueEvent(valueEventListener);
    }
}