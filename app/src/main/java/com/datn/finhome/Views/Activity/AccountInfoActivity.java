package com.datn.finhome.Views.Activity;

import static android.content.ContentValues.TAG;
import static com.datn.finhome.Utils.OverUtils.MY_REQUEST_CODE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Magnifier;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.datn.finhome.Models.Image;
import com.datn.finhome.Models.UserModel;
import com.datn.finhome.R;
import com.datn.finhome.Utils.OverUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class AccountInfoActivity extends AppCompatActivity {
    OverUtils overUtils;

    private AppCompatImageButton btnImageAcc;
    private EditText txtNameAcc, txtSdtAcc, txtMailAcc, txtAddressAcc;
    private Button btnDelAcc;
    private Button btnSaveAcc;
    private Uri uri;
    private AppCompatImageButton btnBack;

    private DatabaseReference reference;
    private FirebaseUser firebaseUser;

    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        initView();
        setAccInfo();
        initListener();
    }

    private void initView() {
        btnBack = findViewById(R.id.btnBack);
        btnImageAcc = findViewById(R.id.imgAccInfo);
        txtNameAcc = findViewById(R.id.txt_name_acc);
        txtSdtAcc = findViewById(R.id.txt_sdt_acc);
        txtMailAcc = findViewById(R.id.txt_mail_acc);
        txtAddressAcc = findViewById(R.id.txt_address_acc);
        btnDelAcc = findViewById(R.id.btn_del_acc);
        btnSaveAcc = findViewById(R.id.btn_save_acc);
    }

    private void setAccInfo(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = firebaseUser.getUid();

        reference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                if(userModel != null){
                    String fullName = userModel.name;
                    String phone = userModel.phoneNumber;
                    String avatar = userModel.avatar;
                    String email = userModel.email;
                    String address = userModel.address;

                    txtNameAcc.setText(fullName);
                    txtSdtAcc.setText(phone);
                    Glide.with(getApplicationContext()).load(avatar).into(btnImageAcc);
                    txtMailAcc.setText(email);
                    txtAddressAcc.setText(address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountInfoActivity.this, "Show", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListener() {
        btnImageAcc.setOnClickListener(view -> onClickRequestPermission());
        btnSaveAcc.setOnClickListener(view -> onClickUpdateAccInfo());
        btnDelAcc.setOnClickListener(view -> onClickDelAccInfo());

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void onClickUpdateAccInfo() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = firebaseUser.getUid();

        String newName = txtNameAcc.getText().toString().trim();
        String newPhone = txtSdtAcc.getText().toString().trim();
        String newMail = txtMailAcc.getText().toString().trim();
        String newAddress = txtAddressAcc.getText().toString().trim();

        if(newName.isEmpty() || newPhone.isEmpty() || newMail.isEmpty()){
            Toast.makeText(AccountInfoActivity.this,"Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        }else if(newMail.trim().length() == 0) {
            overUtils.makeToast(getApplicationContext(),overUtils.ERROR_EMAIL);
        }else if (newName.length() <= 5) {
            OverUtils.makeToast(getApplicationContext(), OverUtils.VALIDATE_NAME);
        }else if(!newPhone.trim().matches("^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$")){
            overUtils.makeToast(getApplicationContext(),overUtils.VALIDATE_PHONE);
        } else {
            reference.child(userId).child("name").setValue(newName);
            reference.child(userId).child("phoneNumber").setValue(newPhone);
            reference.child(userId).child("email").setValue(newMail);
            reference.child(userId).child("address").setValue(newAddress);
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    private void onClickDelAccInfo() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = firebaseUser.getUid();
        reference.child(userId).removeValue();
        firebaseUser.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AccountInfoActivity.this, "Đã xóa tài khoản", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AccountInfoActivity.this, LoginActivity.class));
                        }
                    }
                });

    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    final private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent == null) {
                        return;
                    }

                    Uri uri = intent.getData();
                    setUri(uri);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        setBitmapImageView(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    public void setBitmapImageView(Bitmap bitmapImageView) {
        btnImageAcc.setImageBitmap(bitmapImageView);
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

}