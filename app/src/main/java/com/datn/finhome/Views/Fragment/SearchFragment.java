package com.datn.finhome.Views.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.finhome.Adapter.RoomAdapter;
import com.datn.finhome.IClickItemUserListener;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements IClickItemUserListener {
    private SearchView searchView;
    private RecyclerView recyclerSearch;
    private List<RoomModel> list;
    private DatabaseReference dbReference;

    private RoomAdapter roomAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        dbReference = FirebaseDatabase.getInstance().getReference().child("Room");
        searchView = view.findViewById(R.id.txt_search);

        recyclerSearch = view.findViewById(R.id.recycle_list_search);


    }

    @Override
    public void onStart() {
        super.onStart();
        if (dbReference != null) {
            dbReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            list.add(ds.getValue(RoomModel.class));
                        }
                        RoomAdapter roomAdapter = new RoomAdapter(getContext(), list, roomModel -> {

                        });
                        recyclerSearch.setAdapter(roomAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return false;
                }
            });
        }
    }

    private void search(String s) {
        List<RoomModel> myList = new ArrayList<>();
        for (RoomModel model : list) {
            if (model.getAddress().toLowerCase().contains(s.toLowerCase())) {
                myList.add(model);
            }
        }
        RoomAdapter roomAdapter = new RoomAdapter(getContext(), myList, this);
        recyclerSearch.setAdapter(roomAdapter);

    }

    @Override
    public void onClickItemRoom(RoomModel roomModel) {

    }
}