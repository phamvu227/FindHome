package com.datn.finhome.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.datn.finhome.Interfaces.IClickItemUserListener;
import com.datn.finhome.Models.ReviewModel;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.Models.UserModel;
import com.datn.finhome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class RoomHostAdapter extends RecyclerView.Adapter<RoomHostAdapter.ViewHolder> {
    private  Context context;
    private List<RoomModel> roomModelList;
    private IClickItemUserListener iClickItemUserListener;

    public RoomHostAdapter(Context context, List<RoomModel> roomModelList,  IClickItemUserListener listener) {
        this.context = context;
        this.roomModelList = roomModelList;
        this.iClickItemUserListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RoomModel roomModel = roomModelList.get(position);
        if (roomModel == null) {
            return;
        }
        holder.tvName.setText(roomModel.getName());
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getNumberInstance(locale);
        if (roomModel.getPrice() != null){
            holder.tvPrice.setText(currencyFormat.format(Integer.parseInt (roomModel.getPrice())) + " VNĐ/Phòng");
        }
        holder.tvAddress.setText(roomModel.getAddress());
        Glide.with(context).load(roomModel.getImg()).into(holder.imgRoom);
        holder.btnFavorite.setOnClickListener(v -> {
            //add favorite | xóa favorite
            deleteRoom(roomModel);
            notifyDataSetChanged();
        });
        holder.container.setOnClickListener(v -> {
            iClickItemUserListener.onClickItemRoom(roomModel);
        });


    }
    private void deleteRoom(RoomModel roomModel) {
        AlertDialog.Builder  builder =new AlertDialog.Builder(context);
        builder.setTitle("Delete Room")
                .setMessage("Bạn Chắc chắn Muốn Xóa Phòng trọ này chứ")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Room");
                        reference.child(roomModel.getId())
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "xóa thành công", Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                }).
                setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void loadRoom(RoomModel roomModel, ViewHolder holder) {
        String uid = roomModel.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Room");
        ref.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = ""+snapshot.child("name").getValue();
                String img = ""+snapshot.child("img").getValue();
                holder.tvName.setText(name);
                Glide.with(context).load(img).into(holder.imgRoom);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return roomModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout container;
        private AppCompatImageView imgRoom;
        private TextView tvName, tvPrice, tvAddress;
        private AppCompatCheckBox btnFavorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRoom = itemView.findViewById(R.id.imgRoom);
            tvAddress = itemView.findViewById(R.id.tvAddressRoom);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvName = itemView.findViewById(R.id.tvNameRoom);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            container = itemView.findViewById(R.id.containerRoom);
        }
    }
}
