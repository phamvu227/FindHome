package com.datn.finhome.Views.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.R;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

public class ShowDetailActivity extends AppCompatActivity {
    TextView tvaddress,tvAreaReview,tvTitleRoomReview,tvPriceReview;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        RoomModel roomModel = (RoomModel) bundle.get("Room");
        tvAreaReview = findViewById(R.id.tvAreaReview);
        tvaddress = findViewById(R.id.tvDetailReviews);
        tvTitleRoomReview = findViewById(R.id.tvTitleRoomReview);
        tvPriceReview = findViewById(R.id.tvPriceReview);
        imageView = findViewById(R.id.imgview);

        Picasso.get().load(roomModel.getImg()).into(imageView);
        tvPriceReview.setText(roomModel.getPrice());
        tvTitleRoomReview.setText(roomModel.getDescription());
        Log.d("aaa", String.valueOf(tvTitleRoomReview));
        tvaddress.setText(roomModel.getAddress());
        tvAreaReview.setText(roomModel.getSizeRoom());
    }
}