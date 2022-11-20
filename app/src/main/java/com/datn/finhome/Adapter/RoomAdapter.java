package com.datn.finhome.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.datn.finhome.Models.RoomModel;
import com.datn.finhome.R;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    Context context;
    ArrayList<RoomModel> roomModelArrayList;

    public RoomAdapter(Context context, ArrayList<RoomModel> roomModelArrayList) {
        this.context = context;
        this.roomModelArrayList = roomModelArrayList;
    }

    @NonNull
    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.ViewHolder holder, int position) {
        RoomModel roomModel = roomModelArrayList.get(position);
        holder.btnFavorite.setVisibility(View.GONE);
        holder.tvPrice.setVisibility(View.GONE);
        holder.tvName.setText(roomModel.getName());
        holder.tvAddress.setText(roomModel.getAddress());
        Glide.with(context)
                .load(roomModel.getImage())
                .into(holder.imgThumb);
    }

    @Override
    public int getItemCount() {
        return roomModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView imgThumb;
        TextView tvName, tvPrice, tvAddress;
        AppCompatImageButton btnFavorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgThumb = itemView.findViewById(R.id.imgRoom);
            tvName = itemView.findViewById(R.id.tvNameRoom);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvAddress = itemView.findViewById(R.id.tvAddressRoom);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }
    }
}
