package com.datn.finhome.Views.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.datn.finhome.Adapter.PhotoViewPageAdapter;
import com.datn.finhome.Adapter.RoomAdapter;
import com.datn.finhome.Adapter.RoomAdapterHome;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.Models.photoViewPage;
import com.datn.finhome.R;
import com.datn.finhome.Utils.LoaderDialog;
import com.datn.finhome.Views.Activity.ShowDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    Context context;
    private ViewPager mViewPager;
    private CircleIndicator circleIndicator;
    private RecyclerView rcv;
    private DatabaseReference reference;
    private RoomAdapterHome roomAdapter;
    private List<RoomModel> mRoomModel;
    private List<photoViewPage> list;
//    LoaderDialog loaderDialog;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager.getCurrentItem() == list.size() -1){
                mViewPager.setCurrentItem(0);
            }else {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() +1);
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        LoaderDialog.createDialog(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();


        mRoomModel = new ArrayList<>();
        mViewPager = view.findViewById(R.id.viewPage);
        circleIndicator = view.findViewById(R.id.cir);
        list = getLisphoto();
        PhotoViewPageAdapter photoViewPageAdapter = new PhotoViewPageAdapter(list);
        mViewPager.setAdapter(photoViewPageAdapter);
        circleIndicator.setViewPager(mViewPager);
        handler.postDelayed(runnable,3000);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
              handler.removeCallbacks(runnable);
              handler.postDelayed(runnable,3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rcv = view.findViewById(R.id.rcvRoomMain);
//        rcv.setNestedScrollingEnabled(false);
        reference = FirebaseDatabase.getInstance().getReference("Room");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mRoomModel.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RoomModel roomModel = dataSnapshot.getValue(RoomModel.class);
                    mRoomModel.add(roomModel);
                    roomAdapter = new RoomAdapterHome(getContext(), mRoomModel, roomModel1 -> onClickGoToDetail(roomModel1));
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
                    rcv.setLayoutManager(mLayoutManager);
                    rcv.setAdapter(roomAdapter);

                }
//                    loaderDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });
    }
    private  void onClickGoToDetail(RoomModel roomModel){
        Intent intent = new Intent(getActivity(), ShowDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Room", roomModel);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private List<photoViewPage> getLisphoto(){
        List<photoViewPage> list = new ArrayList<>();
        list.add(new photoViewPage(R.drawable.a1));
        list.add(new photoViewPage(R.drawable.a2));
        list.add(new photoViewPage(R.drawable.a3));
        list.add(new photoViewPage(R.drawable.a4));
        return  list;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable,3000);
    }
}