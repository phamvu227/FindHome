package com.datn.finhome.Views.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.finhome.Adapter.PhotoAdapter;
import com.datn.finhome.Controllers.RoomController;
import com.datn.finhome.Interfaces.IAfterGetAllObject;
import com.datn.finhome.Interfaces.IAfterInsertObject;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.R;
import com.datn.finhome.Utils.ImgUri;
import com.datn.finhome.Utils.LoaderDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class addRoomActivity extends AppCompatActivity {
    EditText edTitle, edLocation, edSizeRoom, edPrice, edDescription;
    AppCompatImageButton btnBack;
    AppCompatButton btnPost2, btnTest;
    RecyclerView recyclerImage;
    ImageView imageView;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    FirebaseAuth firebaseAuth;
    private Uri imgUri;
    String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        edTitle = findViewById(R.id.edit_title);
        edLocation = findViewById(R.id.edit_location);
        imageView = findViewById(R.id.dgAdd_add);
        edSizeRoom = findViewById(R.id.edit_size_room);
        edPrice = findViewById(R.id.edit_price);
        edDescription = findViewById(R.id.edit_description);
        recyclerImage = findViewById(R.id.recyclerImage);
        btnBack = findViewById(R.id.btnBack);
        btnPost2 = findViewById(R.id.btnPost2);
        firebaseAuth =FirebaseAuth.getInstance();
        uid = firebaseAuth.getUid();
        setUpSaveRoom();
        setUpGetImg();



        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

//        photoAdapter = new PhotoAdapter(uri, getApplicationContext(), this);
//        recyclerImage.setLayoutManager(new GridLayoutManager(addRoomActivity.this, 3));
//        recyclerImage.setAdapter(photoAdapter);

        edLocation.setOnClickListener(v -> {
        });

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private final ActivityResultLauncher<String> getImg = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    imageView.setImageURI(uri);
                    imgUri = uri;
                }
            });
    private void setUpGetImg() {
        imageView.setOnClickListener(v -> getImg.launch("image/*"));
    }


    private void setUpSaveRoom() {
        btnPost2.setOnClickListener(v -> {
            check(new IAfterGetAllObject() {
                @Override
                public void iAfterGetAllObject(Object obj) {
                    if (obj != null) {
                        RoomModel roomModel = (RoomModel) obj;
//                        progressDialog.show();
                        getProductImg(new IAfterGetAllObject() {
                            @Override
                            public void iAfterGetAllObject(Object obj) {
                                if(obj != null) {
                                    Uri uri = (Uri) obj;
                                    String imgLink = String.valueOf(uri);
                                    roomModel.setImg(imgLink);
                                    insertRoom(roomModel);
                                } else {
                                    Toast.makeText(addRoomActivity.this, " Luu anh that bai", Toast.LENGTH_SHORT).show();
//                                    progressDialog.cancel();
                                }
                            }

                            @Override
                            public void onError(DatabaseError error) {
                                Toast.makeText(addRoomActivity.this, "LOI", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }



                @Override
                public void onError(DatabaseError error) {
                    Toast.makeText(addRoomActivity.this, "loi", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }






    private void insertRoom(RoomModel roomModel) {
        String key = FirebaseDatabase.getInstance().getReference("Room").push().getKey();
        roomModel.setId(key);
        RoomController.getInstance().insertProduct(roomModel, new IAfterInsertObject() {
            @Override
            public void onSuccess(Object obj) {
                Toast.makeText(addRoomActivity.this, "thanh cong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(DatabaseError exception) {
                Toast.makeText(addRoomActivity.this, "that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProductImg(IAfterGetAllObject iAfterGetAllObject) {
        StorageReference fileRef =
                FirebaseStorage.getInstance().getReference().child(System.currentTimeMillis() + "." + ImgUri.getExtensionFile(getApplicationContext(), imgUri));
        fileRef.putFile(imgUri).addOnSuccessListener(taskSnapshot ->
                        fileRef.getDownloadUrl()
                                .addOnSuccessListener(iAfterGetAllObject::iAfterGetAllObject))
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "That bai", Toast.LENGTH_SHORT).show();
                    iAfterGetAllObject.iAfterGetAllObject(null);
                });
    }


    private void check(IAfterGetAllObject iAfterGetAllObject){
        RoomModel room = new RoomModel();
        String name = edTitle.getText().toString().trim();
        String MoTa = edDescription.getText().toString().trim();
        String size = edSizeRoom.getText().toString().trim();
        String Asdress = edLocation.getText().toString().trim();
        String Price = edPrice.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "vui long nhap", Toast.LENGTH_SHORT).show();
            return;
        }

        room.setName(name);
        room.setAddress(Asdress);
        room.setDescription(MoTa);
        room.setSizeRoom(size);
        room.setPrice(Price);
        room.setUid(uid);
        iAfterGetAllObject.iAfterGetAllObject(room);



    }


}
