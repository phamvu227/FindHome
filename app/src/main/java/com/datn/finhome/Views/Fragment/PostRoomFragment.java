package com.datn.finhome.Views.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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

public class PostRoomFragment extends Fragment {
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_room, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setUpSaveRoom();
        setUpGetImg();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Đăng Bài");


        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

//        photoAdapter = new PhotoAdapter(uri, getApplicationContext(), this);
//        recyclerImage.setLayoutManager(new GridLayoutManager(addRoomActivity.this, 3));
//        recyclerImage.setAdapter(photoAdapter);

        edLocation.setOnClickListener(v -> {
        });
    }

    private void initView(View view) {
        edTitle = view.findViewById(R.id.edit_title);
        edLocation = view.findViewById(R.id.edit_location);
        imageView = view.findViewById(R.id.dgAdd_add);
        edSizeRoom = view.findViewById(R.id.edit_size_room);
        edPrice = view.findViewById(R.id.edit_price);
        edDescription = view.findViewById(R.id.edit_description);
        recyclerImage = view.findViewById(R.id.recyclerImage);
        btnBack = view.findViewById(R.id.btnBack);
        btnPost2 = view.findViewById(R.id.btnPost2);

        toolbar = (Toolbar) view.findViewById(R.id.toobar_post);


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
                                    Toast.makeText(getContext(), " Luu anh that bai", Toast.LENGTH_SHORT).show();
//                                    progressDialog.cancel();
                                }
                            }

                            @Override
                            public void onError(DatabaseError error) {
                                Toast.makeText(getContext(), "LOI", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }



                @Override
                public void onError(DatabaseError error) {
                    Toast.makeText(getContext(), "loi", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "thanh cong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(DatabaseError exception) {
                Toast.makeText(getContext(), "that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProductImg(IAfterGetAllObject iAfterGetAllObject) {
        StorageReference fileRef =
                FirebaseStorage.getInstance().getReference().child(System.currentTimeMillis() + "." + ImgUri.getExtensionFile(getContext(), imgUri));
        fileRef.putFile(imgUri).addOnSuccessListener(taskSnapshot ->
                        fileRef.getDownloadUrl()
                                .addOnSuccessListener(iAfterGetAllObject::iAfterGetAllObject))
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "That bai", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "vui long nhap", Toast.LENGTH_SHORT).show();
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