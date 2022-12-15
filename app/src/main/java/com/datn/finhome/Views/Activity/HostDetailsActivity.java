package com.datn.finhome.Views.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.datn.finhome.Adapter.RoomAdapter;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.Models.UserModel;
import com.datn.finhome.databinding.ActivityHostDetailsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HostDetailsActivity extends AppCompatActivity {
    private ActivityHostDetailsBinding binding;
    private RoomAdapter roomAdapter;
    private List<RoomModel> mRoomModel;
    private DatabaseReference referenceHost, referenceRoom;
    private String id;
    private String numberPhone = "";
    GoogleMap map;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHostDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //binding.mapview.onCreate(savedInstanceState);
        //binding.mapview.getMapAsync(this);

        id = getIntent().getStringExtra("id");

        mRoomModel = new ArrayList<>();

        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.btnMess.setOnClickListener(v -> {
            startActivity(new Intent(this, MessageActivity.class));
        });

        initHost();
        initRoom();

        binding.btnCall.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 100);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + numberPhone));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + numberPhone));
            startActivity(intent);
        }
    }

    private void initHost() {
        referenceHost = FirebaseDatabase.getInstance().getReference("Users");
        referenceHost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel hostModel = dataSnapshot.getValue(UserModel.class);
                    assert hostModel != null;
                    if (Objects.equals(id, hostModel.getUserID())) {
                        binding.tvNameHost.setText(hostModel.getName());
                        binding.tvSdtHost.setText(hostModel.getPhoneNumber().toString());
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

    private void initRoom() {
        referenceRoom = FirebaseDatabase.getInstance().getReference("Room");
        referenceRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RoomModel roomModel = dataSnapshot.getValue(RoomModel.class);
                    assert roomModel != null;
                    if (Objects.equals(roomModel.getUid(), id)) {
                        mRoomModel.add(roomModel);
                        Log.e("TAG", "onDataChange: " + mRoomModel.size() );
                        roomAdapter = new RoomAdapter(HostDetailsActivity.this, mRoomModel, roomModel1 -> {
                            onClickGoToDetail(roomModel1);
                        });
                        binding.rcvHostDetails.setAdapter(roomAdapter);
                        binding.rcvHostDetails.setHasFixedSize(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled: " + error.getMessage());
            }
        });
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        map = googleMap;
//        map.getUiSettings().setMyLocationButtonEnabled(false);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        map.setMyLocationEnabled(true);
//
//        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(43.1, -87.9)));
//    }

    private  void onClickGoToDetail(RoomModel roomModel){
        Intent intent = new Intent(this, ShowDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Room", roomModel);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}