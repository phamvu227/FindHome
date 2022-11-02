package com.datn.finhome.Views.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.datn.finhome.R;
import com.google.firebase.auth.FirebaseAuth;

public class accountView extends Fragment implements View.OnClickListener {
    private Button btnLogout;

    FirebaseAuth firebaseAuth;
    View layout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.activity_user, container, false);

        initControl();

        return layout;
    }
    private void initControl() {

        btnLogout = layout.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLogout:
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                getActivity().finish();
                ;

        }
    }
}
