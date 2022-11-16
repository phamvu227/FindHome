package com.datn.finhome.Views.Activity;

import static com.datn.finhome.Utils.Const.HOST_FIREBASE;
import static com.datn.finhome.Utils.Const.ROOM_FIREBASE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.datn.finhome.Adapter.RoomAdapter;
import com.datn.finhome.Models.HostModel;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.databinding.ActivityHostDetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class HostDetailsActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ActivityHostDetailsBinding binding;
    private RoomAdapter roomAdapter;
    private ArrayList<RoomModel> roomModelArrayList;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHostDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = mDatabase.child(HOST_FIREBASE);
        DatabaseReference roomRef = mDatabase.child(ROOM_FIREBASE);
        ValueEventListener valueEventListenerHost = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HostModel hostModel = snapshot.getValue(HostModel.class);
                assert hostModel != null;
                binding.tvNameHost.setText(hostModel.getName());
                binding.tvAddressHost.setText(hostModel.getAddress());
                binding.tvSdtHost.setText(hostModel.getNumberPhone());
                Glide.with(getApplicationContext())
                        .load(hostModel.getAvatar())
                        .into(binding.imgHost);

                RoomModel roomModel = snapshot.getValue(RoomModel.class);
                assert roomModel != null;
                if(Objects.equals(hostModel.getHostID(), roomModel.getIdHost())){
                    roomModelArrayList.add(new RoomModel(roomModel.getAddress(), roomModel.getAmount(), roomModel.getIdHost(), roomModel.getIdRoom(), roomModel.getImage(), roomModel.getName(), roomModel.getPrice()));
                    roomAdapter = new RoomAdapter(getApplicationContext(), roomModelArrayList);
                    roomAdapter.notifyDataSetChanged();
                    binding.rcvHostDetails.setAdapter(roomAdapter);
                    binding.rcvHostDetails.setHasFixedSize(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("HostDeltails", "onCancelled: ", error.toException());
            }
        };
        usersRef.addListenerForSingleValueEvent(valueEventListenerHost);
        roomRef.addListenerForSingleValueEvent(valueEventListenerHost);
    }
}