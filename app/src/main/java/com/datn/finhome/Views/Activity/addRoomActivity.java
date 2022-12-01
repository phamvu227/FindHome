package com.datn.finhome.Views.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.finhome.Adapter.PhotoAdapter;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class addRoomActivity extends AppCompatActivity {
    EditText edTitle, edLocation, edSizeRoom, edRent, edPriceRent;
    Button btnAddImage, btnPost;
    RecyclerView recyclerImage;
    PhotoAdapter photoAdapter;
    private static final int Read_permission = 101;
    private static final int PICK_IMAGE = 1;
    ArrayList<Uri> uri = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        edTitle = findViewById(R.id.add_title);
        edLocation = findViewById(R.id.add_location);
        edSizeRoom = findViewById(R.id.add_size_room);
        edRent = findViewById(R.id.add_rent);
        edPriceRent = findViewById(R.id.add_price_rent);
        btnAddImage = findViewById(R.id.btn_add_image);
        btnPost = findViewById(R.id.btn_post);
        recyclerImage = findViewById(R.id.recycle_add_img);

        photoAdapter = new PhotoAdapter(uri, getApplicationContext());
        recyclerImage.setLayoutManager(new GridLayoutManager(addRoomActivity.this, 3));
        recyclerImage.setAdapter(photoAdapter);
//        if(ContextCompat.checkSelfPermission(addRoomActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(addRoomActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, Read_permission);
//        }


        edLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(addRoomActivity.this, "abc", Toast.LENGTH_SHORT).show();
            }
        });
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edTitle.getText().toString().trim();
                String address = edLocation.getText().toString().trim();
                String image = edLocation.getText().toString().trim();
                String description = edTitle.getText().toString().trim();
                int price = Integer.parseInt(edTitle.getText().toString().trim());
                String sizeRoom = edTitle.getText().toString().trim();
//                onClickPushData();
            }
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
//                intent.setAction(Intent.ACTION_GET_CONTENT);
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
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        uri.add(imageUri);
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn 10 bức ảnh!", Toast.LENGTH_SHORT).show();
                    }

                }
                photoAdapter.notifyDataSetChanged();
               // textView.setText("Photos (" + uri.size() + ") ");
            } else {
                if (uri.size() < 10) {
                    Uri imageUri = data.getData();
                    uri.add(imageUri);
                } else {
                    Toast.makeText(this, "Bạn chỉ được chọn 10 bức ảnh!", Toast.LENGTH_SHORT).show();
                }
            }
            photoAdapter.notifyDataSetChanged();
            //textView.setText("Photos (" + uri.size() + ") ");
        } else {
            Toast.makeText(this, "Bạn chưa chọn bức ảnh nào!", Toast.LENGTH_SHORT).show();
        }
//        if(requestCode == 1 && resultCode == Activity.RESULT_OK && null != data){
//            if(data.getClipData() != null){
//                int x = data.getClipData().getItemCount();
//                    for (int i =0; i<x; i++){
//                        uri.add(data.getClipData().getItemAt(i).getUri());
//                    }
//                photoAdapter.notifyDataSetChanged();
//                textView.setText("Photo ("+uri.size()+")");
//
//            }else if(data.getData() != null){
//                String imageUrl = data.getData().getPath();
//                uri.add(Uri.parse(imageUrl));
//            }
//        }
    }
    //    private void requestPermissions() {
//        PermissionListener permissionlistener = new PermissionListener() {
//            @Override
//            public void onPermissionGranted() {
////                Toast.makeText(addRoomActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
//                selectImagesFromGallery();
//            }
//
//            @Override
//            public void onPermissionDenied(List<String> deniedPermissions) {
//                Toast.makeText(addRoomActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
//            }
//        };
//        TedPermission.create()
//                .setPermissionListener(permissionlistener)
//                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
//                .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
//                .check();
//    }


    private void onClickPushData(RoomModel roomModel) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");
        myRef.setValue(edTitle.getText().toString().trim());
    }
}
