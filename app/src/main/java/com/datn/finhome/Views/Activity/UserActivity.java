
package com.datn.finhome.Views.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.datn.finhome.R;

public class UserActivity extends AppCompatActivity {
    AppCompatButton btnFavorite, btnChangePass, btnHistory, btnSettingAccount, btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user);
        btnFavorite = findViewById(R.id.btnFavourite);
        btnChangePass = findViewById(R.id.btnChangePass);
        btnHistory = findViewById(R.id.btnHistory);
        btnSettingAccount = findViewById(R.id.btnSettingAccount);
        btnLogOut = findViewById(R.id.btnLogout);
//        btnFavorite.setOnClickListener(view -> {
//            Toast.makeText(UserActivity.this, "yêu thích", Toast.LENGTH_SHORT).show();
//            Intent i = new Intent(UserActivity.this, FavoriteActivity.class);
//            startActivity(i);
//        });
        btnHistory.setOnClickListener(view -> {
            Toast.makeText(UserActivity.this, "Lịch sử", Toast.LENGTH_SHORT).show();
//            Intent i = new Intent(UserActivity.this, HistoryActivity.class);
//            startActivity(i);
        });
//        btnSettingAccount.setOnClickListener(view -> {
//            Toast.makeText(UserActivity.this, "Cài đặt tài khoản", Toast.LENGTH_SHORT).show();
//            Intent i = new Intent(UserActivity.this, SettingAccountActivity.class);
//            startActivity(i);
//        });
        btnChangePass.setOnClickListener(view -> {
            Toast.makeText(UserActivity.this, "Đổi mật khẩu", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(UserActivity.this, ChangePassActivity.class);
            startActivity(i);
        });
        btnLogOut.setOnClickListener(view -> Toast.makeText(UserActivity.this, "Đăng xuất", Toast.LENGTH_SHORT).show());
    }
}