package com.datn.finhome.Views.Activity;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Magnifier;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.datn.finhome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;

public class AccountInfoActivity extends AppCompatActivity {
    private AppCompatImageButton btnImageAcc;
    private EditText txtNameAcc, txtSdtAcc, txtMailAcc, txtAddressAcc;
    private Button btnDelAcc;
    private Button btnSaveAcc;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        initView();
        setAccInfo();
        initListener();
    }

    private void initView() {
        btnImageAcc = findViewById(R.id.imgAccInfo);
        txtNameAcc = findViewById(R.id.txt_name_acc);
        txtSdtAcc = findViewById(R.id.txt_sdt_acc);
        txtMailAcc = findViewById(R.id.txt_mail_acc);
        txtAddressAcc = findViewById(R.id.txt_address_acc);
        btnDelAcc = findViewById(R.id.btn_del_acc);
        btnSaveAcc = findViewById(R.id.btn_save_acc);
    }

    private void setAccInfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        txtNameAcc.setText(user.getDisplayName());
        txtSdtAcc.setText(user.getPhoneNumber());
        txtMailAcc.setText(user.getEmail());
        Glide.with(getApplication()).load(user.getPhotoUrl()).error(R.drawable.img_user).into(btnImageAcc);
    }

    public void showAccInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        String name = user.getDisplayName();
        String sdt = user.getPhoneNumber();
        String mail = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        if (name == null || sdt == null || mail == null) {
            txtNameAcc.setVisibility(View.GONE);
            txtSdtAcc.setVisibility(View.GONE);
            txtMailAcc.setVisibility(View.GONE);
        } else {
            txtNameAcc.setVisibility(View.VISIBLE);
            txtNameAcc.setText(name);
            txtSdtAcc.setVisibility(View.VISIBLE);
            txtSdtAcc.setText(sdt);
            txtMailAcc.setVisibility(View.VISIBLE);
            txtMailAcc.setText(mail);
        }

        Glide.with(this).load(photoUrl).error(R.drawable.img_user).into(btnImageAcc);
    }

    private void initListener() {
        btnImageAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
        btnSaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdateAccInfo();
            }
        });
        btnDelAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDelAccInfo();
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
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
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
            }
    );

    public void setBitmapImageView(Bitmap bitmapImageView) {
        btnImageAcc.setImageBitmap(bitmapImageView);
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    private void onClickUpdateAccInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String strFullName = txtNameAcc.getText().toString().trim();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strFullName)
                .setPhotoUri(uri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AccountInfoActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            showAccInfo();
                        }
                    }
                });
    }

    private void onClickDelAccInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AccountInfoActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}