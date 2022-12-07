package com.datn.finhome.Views.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.datn.finhome.R;
import com.facebook.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.view.Change;

public class ChangePassActivity extends AppCompatActivity {
    EditText edtOldPass,edtNewPass,edtReNewPass;
    Button btnChangePass;
    String pass,newPass,oldPass,reNewPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_the_password);
        edtOldPass = findViewById(R.id.edt_old_password);
        edtNewPass = findViewById(R.id.edt_new_password);
        edtReNewPass = findViewById(R.id.edt_re_new_password);
        btnChangePass = findViewById(R.id.btn_change_pass);
        btnChangePass.setOnClickListener(view -> {
            newPass = edtNewPass.getText().toString().trim();
            reNewPass = edtReNewPass.getText().toString().trim();
            oldPass = edtOldPass.getText().toString().trim();
            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            pass = preferences.getString("pass","");
            if(oldPass.isEmpty() || newPass.isEmpty() || reNewPass.isEmpty()){
                Toast.makeText(ChangePassActivity.this,"Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            }else {
                if(oldPass.equalsIgnoreCase(pass)){
                    if(newPass.equalsIgnoreCase(reNewPass)){
                        onClickChangePass();
                        edtOldPass.setText("");
                        edtNewPass.setText("");
                        edtReNewPass.setText("");
                    }else {
                        Toast.makeText(ChangePassActivity.this,"Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ChangePassActivity.this,"Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onClickChangePass(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(newPass).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(ChangePassActivity.this,"Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChangePassActivity.this,MainMenuActivity.class));
                Log.d("ssssss", pass);
            }else {
                Toast.makeText(ChangePassActivity.this,"Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}