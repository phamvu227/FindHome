package com.datn.finhome.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.os.Bundle;
import android.view.View;

import com.datn.finhome.R;

public class SearchActivity extends AppCompatActivity {
    AppCompatImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        setUpBtnBack();
    }

    private void initView() {
        btnBack = findViewById(R.id.btnBack);
    }

    private void setUpBtnBack() {
        btnBack.setClickable(true);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.onBackPressed();
            }
        });
    }
}