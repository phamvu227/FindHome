package com.datn.finhome.Views.Activity;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.datn.finhome.Adapter.DescriptionAdapter;
import com.datn.finhome.Models.ReviewModel;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.Models.UserModel;
import com.datn.finhome.R;
import com.datn.finhome.databinding.ActivityShowDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ShowDetailActivity extends AppCompatActivity {
    private ActivityShowDetailsBinding binding;
    private DatabaseReference referenceHost;
    private String id;
    RecyclerView recyclerView;
    private FirebaseUser user;
    private RoomModel roomModel;
    private DescriptionAdapter descriptionAdapter;
    private List<ReviewModel> mListDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        roomModel = (RoomModel) bundle.get("Room");

//        mListDescription = new ArrayList<>();
//        mListUser = new ArrayList<>();
        recyclerView = binding.rcvBinhLuan;

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        binding.btnContactReviews.setOnClickListener(v -> {
            Intent intent = new Intent(this, HostDetailsActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("id", id);
            intent.putExtras(bundle1);
            startActivity(intent);
        });

        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        Picasso.get().load(roomModel.getImg()).into(binding.imgview);
        if (roomModel.getPrice() != null) {
            binding.tvPriceReview.setText(roomModel.getPrice().toString());
        }
        binding.tvTitleRoomReview.setText(roomModel.getDescription());
        binding.tvAddressContactReviews.setText(roomModel.getAddress());
        binding.tvAreaReview.setText(roomModel.getSizeRoom());
        binding.tvDetailReviews.setText(roomModel.getDescription());

        referenceHost = FirebaseDatabase.getInstance().getReference("Users");
        referenceHost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel hostModel = dataSnapshot.getValue(UserModel.class);
                    assert hostModel != null;
                    id = hostModel.getUserID();
                    if (Objects.equals(roomModel.getUid(), hostModel.getUserID())) {
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

            }


        });
        initRcvReview();
    }




    private void initRcvReview(){
        mListDescription = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Room");
        reference.child(roomModel.getId()).child("Reviews")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mListDescription.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            ReviewModel reviewModel = dataSnapshot.getValue(ReviewModel.class);
                            mListDescription.add(reviewModel);

                        }
                        descriptionAdapter = new DescriptionAdapter(getApplicationContext(),mListDescription);
                        recyclerView.setAdapter(descriptionAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ShowDetailActivity.this, "Looxi", Toast.LENGTH_SHORT).show();
                    }
                });
//        referenceHost = FirebaseDatabase.getInstance().getReference("Users");
//        referenceHost.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    UserModel userModel = dataSnapshot. getValue(UserModel.class);
//                    assert userModel != null;
//                    mListUser.add(userModel);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("TAG", "onCancelled: " + error.getMessage());
//            }
//        });
//
//
//        referenceHost = FirebaseDatabase.getInstance().getReference("Reviews");
//        referenceHost.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    ReviewModel reviewModel = dataSnapshot.getValue(ReviewModel.class);
//                    assert reviewModel != null;
//                    mListDescription.add(reviewModel);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("TAG", "onCancelled: " + error.getMessage());
//            }
//        });
//        descriptionAdapter = new DescriptionAdapter(this,mListDescription,mListUser);
//        binding.rcvBinhLuan.setAdapter(descriptionAdapter);
    }

    private  void openDialog(){
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
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Room");
                String key = FirebaseDatabase.getInstance().getReference("Reviews").push().getKey();
//                Locale locale = new Locale("fr", "FR");
//                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
//                String date = dateFormat.format(new Date());
                String pattern = "HH:mm:ss MM/dd/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date = simpleDateFormat.format(new Date());
                ReviewModel reviewModel = new ReviewModel(
                        user.getUid().toString(),
                        edtReviews.getText().toString().trim(),
                        roomModel.getId()
                );
                reviewModel.setIdComment(key);
                reviewModel.setTime(date);
                mDatabase.child(roomModel.getId()).child("Reviews").push().setValue(reviewModel, (databaseError, databaseReference) -> {
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