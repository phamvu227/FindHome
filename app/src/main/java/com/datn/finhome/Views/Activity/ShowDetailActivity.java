package com.datn.finhome.Views.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.datn.finhome.Adapter.AdapterFavorite;
import com.datn.finhome.Adapter.DescriptionAdapter;
import com.datn.finhome.Models.ReviewModel;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.R;
import com.datn.finhome.databinding.ActivityShowDetailsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ShowDetailActivity extends AppCompatActivity {

    private ActivityShowDetailsBinding binding;
    private DatabaseReference referenceHost;
    boolean isInMyFavorite = false;
    FirebaseAuth firebaseAuth;
    String uid;
    ImageView imageView;
    RecyclerView recyclerView;
    private FirebaseUser user;
    RoomModel roomModel;
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

        recyclerView = binding.rcvBinhLuan;
        imageView = binding.btnFavoriteReview;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() == null){

                }
                else {
                    if (isInMyFavorite){
                        removeFavorite(getApplicationContext(),roomModel.getId());
                    }else {
                        addToFavorite(getApplicationContext(),roomModel.getId());
                    }
                }
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            checkIsFavorite();
        }

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        binding.btnContactReviews.setOnClickListener(v -> {
            Intent intent = new Intent(this, HostDetailsActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("id", roomModel.getUid());
            intent.putExtras(bundle1);
            startActivity(intent);
        });

        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        Picasso.get().load(roomModel.getImg()).into(binding.imgview);
        if (roomModel.getPrice() != null) {
            binding.tvPriceReview.setText(roomModel.getPrice().toString()+"VND/phòng");
        }
        binding.tvTitleRoomReview.setText(roomModel.getName());
//        binding.tvAddressContactReviews.setText(roomModel.getAddress());
        binding.tvAreaReview.setText(roomModel.getSizeRoom()+"m2");
        binding.tvDetailReviews.setText(roomModel.getDescription());
        String uid = roomModel.getUid();
        referenceHost = FirebaseDatabase.getInstance().getReference("Users");
        referenceHost.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = ""+snapshot.child("name").getValue();
                String address = ""+snapshot.child("address").getValue();
                String img = ""+snapshot.child("avatar").getValue();
                binding.tvNameContactReviews.setText(name);
                binding.tvAddressContactReviews.setText(address);
                Glide.with(getApplicationContext())
                        .load(img)
                        .into(binding.imgContactReviews);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("",error.getMessage());

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
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Room");
                String key = FirebaseDatabase.getInstance().getReference("Reviews").push().getKey();
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
                if (dialog != null && dialog.isShowing()) {
                   dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                       @Override
                       public void onDismiss(DialogInterface dialog) {
                           dialog.dismiss();
                       }
                   });
                }
            }
        });

        dialog.show();
    }
    public void reviews(View view) {
        openDialog();
    }


    public  void checkIsFavorite(){
      DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
      reference.child(firebaseAuth.getUid()).child("favorites").child(roomModel.getId())
              .addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      isInMyFavorite = snapshot.exists();
                      if (isInMyFavorite){
//                          imageView.setCompoun
//                          imageView.setImageDrawable(Drawable.);
                      }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });
    }



    public void addToFavorite(Context context, String roomId){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            Toast.makeText(context, "vui long dang nhap", Toast.LENGTH_SHORT).show();
        }else {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("roomId",""+roomId);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(firebaseAuth.getUid()).child("favorites").child(roomId).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Da them vao danh sach yeu thich", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "That bai", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    public static void removeFavorite(Context context,String roomId){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            Toast.makeText(context, "vui long dang nhap", Toast.LENGTH_SHORT).show();
        }else {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(firebaseAuth.getUid()).child("favorites").child(roomId).removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Bo yeu thich", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "That bai", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}