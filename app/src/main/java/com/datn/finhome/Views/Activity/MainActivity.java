package com.datn.finhome.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.datn.finhome.Adapter.roomAdapter;
import com.datn.finhome.Models.roomModel;
import com.datn.finhome.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lstVRoom;
    ArrayList<roomModel> mydata;
    roomAdapter roomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    roomAdapter = new roomAdapter(this,R.layout.rom_element_list_view,mydata);
    lstVRoom.setAdapter(roomAdapter);
    lstVRoom.setClickable(false);
        //Ducbao commit
    }

    private void initData()
    {
        lstVRoom=(ListView) findViewById(R.id.lstV_room);

        mydata=new ArrayList<>();

        mydata.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","66 Thái Hà, Đống Đa, Hà Nội", 8, 256, "PHÒNG TRỌ"));
        mydata.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","66 Thái Hà, Đống Đa, Hà Nội", 6, 18, "PHÒNG TRỌ"));
        mydata.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","66 Thái Hà, Đống Đa, Hà Nội", 5, 365, "CHUNG CƯ"));
        mydata.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","66 Thái Hà, Đống Đa, Hà Nội", 4, 256, "PHÒNG TRỌ"));
        mydata.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","66 Thái Hà, Đống Đa, Hà Nội", 6, 28, "KÍ TÚC XÁ"));
        mydata.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","66 Thái Hà, Đống Đa, Hà Nội", 7, 147, "PHÒNG TRỌ"));
    }
}