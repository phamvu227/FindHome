package com.datn.finhome.Views.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.finhome.Adapter.PhotoAdapter;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.R;
import com.datn.finhome.Utils.LoaderDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class addRoomActivity extends AppCompatActivity implements PhotoAdapter.CountOfImageWhenRemove {
    EditText edTitle, edLocation, edSizeRoom, edPrice, edDescription;
    AppCompatImageButton btnBack;
    AppCompatButton btnAddImage, btnPost2;
    RecyclerView recyclerImage;
    PhotoAdapter photoAdapter;
    private static final int Read_permission = 101;
    private static final int PICK_IMAGE = 1;
    ArrayList<Uri> uri = new ArrayList<>();
    private Uri imageUri;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    private int upload_count = 0;
    List<String> pictureLink;
    Long idHost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        edTitle = findViewById(R.id.edit_title);
        edLocation = findViewById(R.id.edit_location);
        edSizeRoom = findViewById(R.id.edit_size_room);
        edPrice = findViewById(R.id.edit_price);
        edDescription = findViewById(R.id.edit_description);
        btnAddImage = findViewById(R.id.btnAddImage);
        recyclerImage = findViewById(R.id.recyclerImage);
        btnBack = findViewById(R.id.btnBack);
        btnPost2 = findViewById(R.id.btnPost2);

        btnPost2.setOnClickListener(v -> {
            LoaderDialog.createDialog(this);
            onClickPushData2();
            uploadToFirebase();
        });

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        idHost = Long.valueOf((String) bundle.get("id"));

        photoAdapter = new PhotoAdapter(uri, getApplicationContext(), this);
        recyclerImage.setLayoutManager(new GridLayoutManager(addRoomActivity.this, 3));
        recyclerImage.setAdapter(photoAdapter);

        edLocation.setOnClickListener(v -> {
        });

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(addRoomActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(addRoomActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Read_permission);

                    return;
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
                startActivityForResult(intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {
                int countOfImages = data.getClipData().getItemCount();
                for (int i = 0; i < countOfImages; i++) {
                    if (uri.size() < 10) {
                        imageUri = data.getClipData().getItemAt(i).getUri();
                        uri.add(imageUri);
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn 10 bức ảnh!", Toast.LENGTH_SHORT).show();
                    }
                }
                photoAdapter.notifyDataSetChanged();
            } else {
                if (uri.size() < 10) {
                    imageUri = data.getData();
                    uri.add(imageUri);
                } else {
                    Toast.makeText(this, "Bạn chỉ được chọn 10 bức ảnh!", Toast.LENGTH_SHORT).show();
                }
            }
            photoAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Bạn chưa chọn bức ảnh nào!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickPushData2() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        RoomModel roomModel2 = new RoomModel(
                edTitle.getText().toString().trim(),
                edLocation.getText().toString().trim(),
                edSizeRoom.getText().toString().trim(),
                Long.valueOf(edPrice.getText().toString().trim()),
                edDescription.getText().toString().trim(),
                idHost
        );
        mDatabase.child("Room").push().setValue(roomModel2, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    LoaderDialog.dismiss();
                    Toast.makeText(addRoomActivity.this, "Lỗi: " + databaseError + "", Toast.LENGTH_SHORT).show();
                } else {
                    LoaderDialog.dismiss();
                    Toast.makeText(addRoomActivity.this, "Đã đăng bài", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void uploadToFirebase() {
        StorageReference imageFolder = FirebaseStorage.getInstance().getReference().child("ImageFolder");

        for (upload_count = 0; upload_count < uri.size(); upload_count++) {
            Uri IndividualImage = uri.get(upload_count);
            StorageReference ImageName = imageFolder.child("Image" + IndividualImage.getLastPathSegment());

            ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = String.valueOf(uri);
                                    StoreLink(url);
                                    LoaderDialog.dismiss();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            LoaderDialog.dismiss();
                            Toast.makeText(addRoomActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void StoreLink(String url) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Room");

        DatabaseReference childDatabase = databaseReference.child("Images");

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Imglink", url);
        childDatabase.push().setValue(hashMap);
    }

    @Override
    public void clicked(int getSize) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
