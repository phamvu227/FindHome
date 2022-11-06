package com.datn.finhome.Views.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.datn.finhome.R;
import com.google.firebase.auth.FirebaseAuth;

public class accountView extends Fragment implements View.OnClickListener {
    private Button btnLogout;
    private TextView tvName,tvEmail;
    ImageView ImgAvt;
    FirebaseAuth firebaseAuth;
    View layout;
    private Context mContext;
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
        tvName = layout.findViewById(R.id.tvNameUser);
        tvEmail = layout.findViewById(R.id.tvAddressUser);
        ImgAvt = layout.findViewById(R.id.imgUser);
        btnLogout.setOnClickListener(this);
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userName = preferences.getString("username","");
        String userEmail = preferences.getString("useremail","");
        String userAvt = preferences.getString("userAvatar","");

        tvName.setText(userName);
        tvEmail.setText(userEmail);
        Glide.with(this).load(userAvt).into(ImgAvt);

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void signout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Bạn có chắc muốn đăng xuất?")
                .setPositiveButton("CÓ", (dialog, which) -> {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
//                startActivity(new Intent(context,LoginActivity.class));
                    getActivity().finish();
                })
                .setNegativeButton("HỦY", (dialog, which) -> dialog.dismiss());
        Dialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLogout:
                signout();

        }
    }

}
