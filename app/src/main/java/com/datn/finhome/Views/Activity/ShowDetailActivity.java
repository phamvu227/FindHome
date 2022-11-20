package com.datn.finhome.Views.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.datn.finhome.R;
import com.google.android.material.button.MaterialButton;

public class ShowDetailActivity extends AppCompatActivity {
    TextView tvTitleRoomReview,tvStarReview,tvAreaReview,tvPriceReview,tvNameContactReviews,
            tvAddressContactReviews,tvDetailReviews;
    ImageView imgContactReviews;
    MaterialButton btnContactReviews;
    String title,star,area,price,detailreview;
    SharedPreferences preferences = getSharedPreferences("MyDetailRoom", MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        tvTitleRoomReview = findViewById(R.id.tvTitleRoomReview);
        tvStarReview = findViewById(R.id.tvStarReview);
        tvAreaReview = findViewById(R.id.tvAreaReview);
        tvPriceReview = findViewById(R.id.tvPriceReview);
        tvNameContactReviews = findViewById(R.id.tvNameContactReviews);
        tvAddressContactReviews = findViewById(R.id.tvAddressContactReviews);
        tvDetailReviews = findViewById(R.id.tvDetailReviews);
        imgContactReviews = findViewById(R.id.imgContactReviews);
        btnContactReviews = findViewById(R.id.btnContactReviews);
        title = preferences.getString("title","");
        star = preferences.getString("star","");
        area = preferences.getString("area","");
        price = preferences.getString("price","");
        detailreview = preferences.getString("detailreview","");
        getDetail();
    }

    void getDetail(){
        tvTitleRoomReview.setText(title);
        tvStarReview.setText(star);
        tvAreaReview.setText(area);
        tvPriceReview.setText(price);
        tvDetailReviews.setText(detailreview);
    }
}