package com.datn.finhome.Views.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.datn.finhome.Adapter.AdapterFavorite;
import com.datn.finhome.Models.ReviewModel;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.R;
import com.datn.finhome.databinding.ActivityFavoriteBinding;
import com.datn.finhome.databinding.ActivityShowDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private ActivityFavoriteBinding binding;
    private ArrayList<RoomModel> roomModels;
    private AdapterFavorite adapterFavorite;
    RecyclerView rvRoomFv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        rvRoomFv = binding.rcvFavorite;

        loadFavorite();

    }

    private void loadFavorite() {
        roomModels = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("favorites")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       roomModels.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String roomId = "" + dataSnapshot.child("roomId").getValue();

                            RoomModel roomModel = new RoomModel();
                            roomModel.setId(roomId);
                            roomModels.add(roomModel);
                            Log.d("tag", "" + roomModels);
                        }
                        adapterFavorite = new AdapterFavorite(FavoriteActivity.this, roomModels);
                        rvRoomFv.setAdapter(adapterFavorite);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("", error.getMessage());
                    }
                });
    }
}