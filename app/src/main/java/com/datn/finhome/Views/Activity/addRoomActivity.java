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


import androidx.annotation.NonNull;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class addRoomActivity extends AppCompatActivity implements PhotoAdapter.CountOfImageWhenRemove{
    EditText edTitle, edLocation, edSizeRoom, edPrice, edDescription;
    Button btnAddImage, btnPost;
    RecyclerView recyclerImage;
    PhotoAdapter photoAdapter;
    TextView textView;
    private static final int Read_permission = 101;
    private static final int PICK_IMAGE = 1;
    ArrayList<Uri> uri = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        edTitle = findViewById(R.id.edit_title);
        edLocation = findViewById(R.id.edit_location);
        edSizeRoom = findViewById(R.id.edit_size_room);
        edPrice = findViewById(R.id.edit_price);
        edDescription = findViewById(R.id.edit_description);
        btnAddImage = findViewById(R.id.btn_add_image);
        btnPost = findViewById(R.id.btn_post);
        recyclerImage = findViewById(R.id.recyclerImage);
        textView = findViewById(R.id.textTest);

        photoAdapter = new PhotoAdapter(uri, getApplicationContext(), this);
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
                onClickPushData();
//                String title = edTitle.getText().toString().trim();
//                String address = edLocation.getText().toString().trim();
//                String image = edLocation.getText().toString().trim();
//                String description = edTitle.getText().toString().trim();
//                int price = Integer.parseInt(edTitle.getText().toString().trim());
//                String sizeRoom = edTitle.getText().toString().trim();

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
                textView.setText("Photos (" + uri.size() + ") ");
            } else {
                if (uri.size() < 10) {
                    Uri imageUri = data.getData();
                    uri.add(imageUri);
                } else {
                    Toast.makeText(this, "Bạn chỉ được chọn 10 bức ảnh!", Toast.LENGTH_SHORT).show();
                }
            }
            photoAdapter.notifyDataSetChanged();
            textView.setText("Photos (" + uri.size() + ") ");
        } else {
            Toast.makeText(this, "Bạn chưa chọn bức ảnh nào!", Toast.LENGTH_SHORT).show();
        }

    }


    private void onClickPushData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Room");
//        myRef.setValue(edTitle.getText().toString().trim());
        RoomModel roomModel1 = new RoomModel(edTitle.getText().toString().trim(),
                edLocation.getText().toString().trim(),
                edSizeRoom.getText().toString().trim(),
                edPrice.getText().toString().trim(),
                edDescription.getText().toString().trim()
                );

        myRef.setValue(roomModel1, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(addRoomActivity.this, "đã thêm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void clicked(int getSize) {
        textView.setText("Photos (" + uri.size() + ") ");
    }
}
