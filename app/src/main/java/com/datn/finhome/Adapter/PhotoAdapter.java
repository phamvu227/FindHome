package com.datn.finhome.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.datn.finhome.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{
    private ArrayList<Uri> uriArrayList;
    private Context context;

    public PhotoAdapter(ArrayList<Uri> uriArrayList, Context context) {
        this.uriArrayList = uriArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_photo, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.ViewHolder holder, int position) {
//        holder.imageView.setImageURI(uriArrayList.get(position));
        Glide.with(context)
                .load(uriArrayList.get(position))
                .into(holder.imageView);

        holder.deleteImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uriArrayList.remove(uriArrayList.get(position));
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, deleteImages;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgAdd);
            deleteImages = itemView.findViewById(R.id.deleteImage);
        }
    }
}
