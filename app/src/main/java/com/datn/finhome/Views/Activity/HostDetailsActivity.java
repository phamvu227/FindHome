package com.datn.finhome.Views.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.datn.finhome.Adapter.RoomAdapter;
import com.datn.finhome.IClickItemUserListener;
import com.datn.finhome.Models.HostModel;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.databinding.ActivityHostDetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class HostDetailsActivity extends AppCompatActivity {
    private ActivityHostDetailsBinding binding;
    private RoomAdapter roomAdapter;
    private List<RoomModel> mRoomModel;
    private DatabaseReference referenceHost, referenceRoom;
    private Long id = Long.valueOf(10001); //id host

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHostDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.btnMess.setOnClickListener(v -> {
            startActivity(new Intent(this, MessageActivity.class));
        });

        binding.btnCall.setOnClickListener(v -> {
            //call
        });

        initHost();
        initRoom();
        binding.rcvHostDetails.setAdapter(roomAdapter);
    }

    private void initHost(){
        referenceHost = FirebaseDatabase.getInstance().getReference("Host");
        referenceHost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HostModel hostModel = dataSnapshot.getValue(HostModel.class);
                    assert hostModel != null;
                    if (Objects.equals(id, hostModel.getId())){
                        binding.tvNameHost.setText(hostModel.getName());
                        binding.tvSdtHost.setText(hostModel.getNumber_phone().toString());
                        binding.tvAddressHost.setText(hostModel.getAddress());
                        Glide.with(binding.getRoot())
                                .load(hostModel.getAvatar())
                                .into(binding.imgHost);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled: " + error.getMessage());
            }
        });
    }

    private void initRoom(){
        referenceRoom = FirebaseDatabase.getInstance().getReference("Room");
        referenceRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RoomModel roomModel = dataSnapshot.getValue(RoomModel.class);
                    assert roomModel != null;
                    if (Objects.equals(roomModel.getIdHost(), id)){
                        mRoomModel.add(roomModel);
                        roomAdapter = new RoomAdapter(HostDetailsActivity.this, mRoomModel, new IClickItemUserListener() {
                            @Override
                            public void onClickItemRoom(RoomModel roomModel) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled: " + error.getMessage());
            }
        });
    }
}