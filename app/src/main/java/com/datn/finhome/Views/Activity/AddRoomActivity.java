package com.datn.finhome.Views.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.datn.finhome.Controllers.RoomController;
import com.datn.finhome.Interfaces.IAfterGetAllObject;
import com.datn.finhome.Interfaces.IAfterInsertObject;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.R;
import com.datn.finhome.Utils.ImgUri;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRoomActivity extends AppCompatActivity {
    EditText edTitle, edLocation, edSizeRoom, edPrice, edDescription;
    AppCompatImageButton btnBack;
    AppCompatButton btnPost2, btnTest;
    RecyclerView recyclerImage;
    ImageView imageView;

    Toolbar toolbar;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    FirebaseAuth firebaseAuth;
    private Uri imgUri;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        initView();
        setUpSaveRoom();
        setUpGetImg();
    }
    private void initView() {
        edTitle = findViewById(R.id.edit_title);
        edLocation =findViewById(R.id.edit_location);
        imageView = findViewById(R.id.dgAdd_add);
        edSizeRoom = findViewById(R.id.edit_size_room);
        edPrice = findViewById(R.id.edit_price);
        edDescription = findViewById(R.id.edit_description);
        recyclerImage = findViewById(R.id.recyclerImage);
        btnBack = findViewById(R.id.btnBack);
        btnPost2 = findViewById(R.id.btnPost2);



        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getUid();
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
                                    Toast.makeText(getApplicationContext(), " Luu anh that bai", Toast.LENGTH_SHORT).show();
//                                    progressDialog.cancel();
                                }
                            }

                            @Override
                            public void onError(DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "LOI", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }



                @Override
                public void onError(DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "loi", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "thanh cong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(DatabaseError exception) {
                Toast.makeText(getApplicationContext(), "that bai", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "That bai", Toast.LENGTH_SHORT).show();
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
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        if (name.isEmpty() && MoTa.isEmpty() && size.isEmpty() && Asdress.isEmpty() && Price.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        room.setName(name);
        room.setAddress(Asdress);
        room.setDescription(MoTa);
        room.setSizeRoom(size);
        room.setPrice(Price);
        room.setUid(uid);
        room.setTime(date);
        iAfterGetAllObject.iAfterGetAllObject(room);



    }

}