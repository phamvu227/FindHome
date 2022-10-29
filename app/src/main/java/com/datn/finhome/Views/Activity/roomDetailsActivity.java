package com.datn.finhome.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.datn.finhome.Adapter.commentRoomAdapter;
import com.datn.finhome.Adapter.roomAdapter;
import com.datn.finhome.Adapter.utilityRoomAdapter;
import com.datn.finhome.Models.commentRoomModel;
import com.datn.finhome.Models.roomModel;
import com.datn.finhome.Models.utilityRoomModel;
import com.datn.finhome.R;

import java.util.ArrayList;

public class roomDetailsActivity extends AppCompatActivity {

    GridView grVUtilitiyRoomDetail;
    GridView grVSameCriteriaRoomDetail;

    ListView lstVCommentRoomDetail;

    ArrayList<utilityRoomModel> lstUtilityRoom;
    ArrayList<roomModel> lstSameCriteriaRoom;
    ArrayList<commentRoomModel> lstCommentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        initControl();

        initDataUtilitiy();

        initDataSameCriteria();

        initDataComment();

        adapter();
    }

    private void initControl() {
        grVUtilitiyRoomDetail = (GridView) findViewById(R.id.grV_utiliti_rom_detail);
        grVSameCriteriaRoomDetail = (GridView) findViewById(R.id.grV_sameCriteria_rom_detail);
        lstVCommentRoomDetail = (ListView) findViewById(R.id.lst_comment_rom_detail);
    }

    private void initDataUtilitiy() {
        lstUtilityRoom = new ArrayList<>();

        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_aircondition_100, "Máy lạnh"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_avt_01_100, "WC riêng"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_avt_01_100, "Chỗ để xe"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_avt_01_100, "Wifi"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_avt_01_100, "Tự do"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_avt_01_100, "Chủ riêng"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_avt_01_100, "Tủ lạnh"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_avt_01_100, "Máy giặt"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_avt_01_100, "An ninh"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_avt_01_100, "Giường"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_avt_01_100, "Tủ để đồ"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_avt_01_100, "Cửa số"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_avt_01_100, "Máy nước nóng"));
    }

    private void initDataSameCriteria() {
        lstSameCriteriaRoom = new ArrayList<>();

        lstSameCriteriaRoom.add(new roomModel(R.drawable.avt_jpg_room,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","191 pháo dài láng, đống đa hà nội", 8, 256, "PHÒNG TRỌ"));
        lstSameCriteriaRoom.add(new roomModel(R.drawable.avt_jpg_room,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","191 pháo dài láng, đống đa hà nội", 6, 18, "PHÒNG TRỌ"));
        lstSameCriteriaRoom.add(new roomModel(R.drawable.avt_jpg_room,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","191 pháo dài láng, đống đa hà nội", 5, 365, "CHUNG CƯ"));
        lstSameCriteriaRoom.add(new roomModel(R.drawable.avt_jpg_room,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","191 pháo dài láng, đống đa hà nội", 4, 256, "PHÒNG TRỌ"));
        lstSameCriteriaRoom.add(new roomModel(R.drawable.avt_jpg_room,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","191 pháo dài láng, đống đa hà nội", 6, 28, "KÍ TÚC XÁ"));
        lstSameCriteriaRoom.add(new roomModel(R.drawable.avt_jpg_room,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","191 pháo dài láng, đống đa hà nội", 7, 147, "PHÒNG TRỌ"));
    }

    private void initDataComment() {
        lstCommentRoom = new ArrayList<>();

        lstCommentRoom.add(new commentRoomModel(R.drawable.ic_svg_avt_01_100,"Lê Đức Bảo",5,7, "Phòng rất tốt", "Mình tới tìm phòng này thì thấy phòng đăng khá đúng với thông tin trên app, khá hài lòng"));
        lstCommentRoom.add(new commentRoomModel(R.drawable.ic_svg_avt_02_100,"Đinh Văn Thức",7,7, "Cảm thấy rất tuyệt vời", "Rất hài lòng...."));
        lstCommentRoom.add(new commentRoomModel(R.drawable.ic_svg_avt_03_100,"Lê văn C",12,7, "Chỗ ở không đúng mô tả", "Chỗ này mình tới rồi mọi người không đúng như mô tả đâu mn ơi"));
        lstCommentRoom.add(new commentRoomModel(R.drawable.ic_svg_avt_04_100,"Nguyễn thị c",3,7, "Chỗ ở tệ", "Chỗ ở quá chật thiếu nhiều các tiện ít mà giá còn đắt hơn chỗ khác nữa chứ!!"));
    }

    private void adapter() {
        utilityRoomAdapter adapterUtilityRoom = new utilityRoomAdapter(this, R.layout.utility_element_grid_rom_detail_view, lstUtilityRoom);
        roomAdapter adapterRoom = new roomAdapter(this, R.layout.rom_element_grid_view, lstSameCriteriaRoom);
        commentRoomAdapter adapterComment = new commentRoomAdapter(this, R.layout.comment_element_grid_room_detail_view, lstCommentRoom);

        grVUtilitiyRoomDetail.setAdapter(adapterUtilityRoom);
        grVSameCriteriaRoomDetail.setAdapter(adapterRoom);
        lstVCommentRoomDetail.setAdapter(adapterComment);

        grVUtilitiyRoomDetail.setClickable(false);
        grVSameCriteriaRoomDetail.setClickable(false);
        lstVCommentRoomDetail.setClickable(false);
    }
}