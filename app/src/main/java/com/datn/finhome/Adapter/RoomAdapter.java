package com.datn.finhome.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.datn.finhome.IClickItemUserListener;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.R;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewholder> {
    Context context;
    private List<RoomModel> list;
    private IClickItemUserListener iClickItemUserListener;

    public RoomAdapter(Context context, List<RoomModel> list, IClickItemUserListener listener) {
        this.context = context;
        this.list = list;
        this.iClickItemUserListener = listener;
    }

    public void setList(List<RoomModel> list){
        this.list=list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        RoomAdapter.RoomViewholder roomViewholder = new RoomAdapter.RoomViewholder(view);
        return roomViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewholder holder, int position) {
        RoomModel roomModel = list.get(position);
        holder.tvName.setText(roomModel.getName());
        holder.tvPrice.setText(roomModel.getPrice());
        holder.tvAddress.setText(roomModel.getAddress());
        Glide.with(context).load(roomModel.getImg()).into(holder.imgRoom);
        holder.btnFavorite.setOnClickListener(v -> {
            //add favorite | xóa favorite
        });
        holder.container.setOnClickListener(v -> {
            iClickItemUserListener.onClickItemRoom(roomModel);
            // chuyển màn + truyền dữ liệu
        });
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    public class RoomViewholder extends RecyclerView.ViewHolder {
        private ConstraintLayout container;
        private AppCompatImageView imgRoom;
        private TextView tvName, tvPrice, tvAddress;
        private AppCompatCheckBox btnFavorite;
        public RoomViewholder(@NonNull View itemView) {
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
