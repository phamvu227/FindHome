//
//package com.datn.finhome.Views.Activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.AppCompatButton;
//
//import com.bumptech.glide.Glide;
//import com.datn.finhome.Models.UserModel;
//import com.datn.finhome.R;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class UserActivity extends AppCompatActivity {
//    AppCompatButton btnFavorite, btnChangePass, btnHistory, btnSettingAccount, btnLogOut;
//    TextView tvName,tvPhone,tvAssdres;
//    ImageView imgUser;
//    private String userId;
//    private FirebaseUser firebaseUser;
//    private DatabaseReference databaseReference;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_user);
//        btnFavorite = findViewById(R.id.btnFavourite);
//        btnChangePass = findViewById(R.id.btnChangePass);
//        btnSettingAccount = findViewById(R.id.btnSettingAccount);
//        tvName = findViewById(R.id.tvNameUser2);
//        tvPhone = findViewById(R.id.tvSdtUser2);
//        imgUser = findViewById(R.id.imgUser2);
//        btnLogOut = findViewById(R.id.btnLogout);
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
//        userId = firebaseUser.getUid();
//        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UserModel userModel = snapshot.getValue(UserModel.class);
//                if(userModel != null){
//                    String fullName = userModel.name;
//                    String Phone = userModel.phoneNumber;
//                    String avatar = userModel.avatar;
//
//                    tvName.setText(fullName);
//                    tvPhone.setText(Phone);
//                    Glide.with(getApplicationContext()).load(avatar).into(imgUser);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(UserActivity.this, "That bai", Toast.LENGTH_SHORT).show();
//            }
//        });
//        btnFavorite.setOnClickListener(view -> {
////            Toast.makeText(UserActivity.this, "yêu thích", Toast.LENGTH_SHORT).show();
////            Intent i = new Intent(UserActivity.this, FavoriteActivity.class);
////            startActivity(i);
//        });
//        btnHistory.setOnClickListener(view -> {
//            Toast.makeText(UserActivity.this, "Lịch sử", Toast.LENGTH_SHORT).show();
////            Intent i = new Intent(UserActivity.this, HistoryActivity.class);
////            startActivity(i);
//        });
//        btnSettingAccount.setOnClickListener(view -> {
//            Intent i = new Intent(UserActivity.this, AccountInfoActivity.class);
//            startActivity(i);
//        });
//        btnChangePass.setOnClickListener(view -> {
//            Toast.makeText(UserActivity.this, "Đổi mật khẩu", Toast.LENGTH_SHORT).show();
//            Intent i = new Intent(UserActivity.this, ChangePassActivity.class);
//            startActivity(i);
//        });
//        btnLogOut.setOnClickListener(view -> Toast.makeText(UserActivity.this, "Đăng xuất", Toast.LENGTH_SHORT).show());
//    }
//}