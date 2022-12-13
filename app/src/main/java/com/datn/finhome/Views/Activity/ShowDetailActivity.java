package com.datn.finhome.Views.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.bumptech.glide.Glide;
import com.datn.finhome.Models.HostModel;
import com.datn.finhome.Models.ReviewModel;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.R;
import com.datn.finhome.Utils.LoaderDialog;
import com.datn.finhome.databinding.ActivityHostDetailsBinding;
import com.datn.finhome.databinding.ActivityShowDetailsBinding;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ShowDetailActivity extends AppCompatActivity {
    private ActivityShowDetailsBinding binding;
    private DatabaseReference referenceHost;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        RoomModel roomModel = (RoomModel) bundle.get("Room");

        binding.btnContactReviews.setOnClickListener(v -> {
            Intent intent = new Intent(this, HostDetailsActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putLong("id", id);
            intent.putExtras(bundle1);
            startActivity(intent);
        });

        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        Picasso.get().load(roomModel.getImg()).into(binding.imgview);
        if (roomModel.getPrice() != null){
            binding.tvPriceReview.setText(roomModel.getPrice().toString());
        }
        binding.tvTitleRoomReview.setText(roomModel.getDescription());
        binding.tvAddressContactReviews.setText(roomModel.getAddress());
        binding.tvAreaReview.setText(roomModel.getSizeRoom());
        binding.tvDetailReviews.setText(roomModel.getDescription());

        referenceHost = FirebaseDatabase.getInstance().getReference("Host");
        referenceHost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HostModel hostModel = dataSnapshot.getValue(HostModel.class);
                    assert hostModel != null;
                    id = hostModel.getId();
                    if (Objects.equals(roomModel.getIdHost(), hostModel.getId())){
                        binding.tvNameContactReviews.setText(hostModel.getName());
                        binding.tvAddressContactReviews.setText(hostModel.getAddress());
                        Glide.with(getApplicationContext())
                                .load(hostModel.getAvatar())
                                .into(binding.imgContactReviews);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled: " + error.getMessage());
            }
        });
    }

    private  void openDialog(){
//        final Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.review_dialog);
//        dialog.setTitle("AA");
//        dialog.show();
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.review_dialog);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowatribute = window.getAttributes();
        windowatribute.gravity = Gravity.CENTER;
        window.setAttributes(windowatribute);

        EditText edtReviews = dialog.findViewById(R.id.edtReviews);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSend = dialog.findViewById(R.id.btnSend);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                ReviewModel reviewModel = new ReviewModel(
                        edtReviews.getText().toString().trim()
                );
                mDatabase.child("Reviews").push().setValue(reviewModel, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        Toast.makeText(ShowDetailActivity.this, "Lỗi: " + databaseError + "", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ShowDetailActivity.this, "Đã gửi bình luận", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void reviews(View view) {
        openDialog();
    }
}