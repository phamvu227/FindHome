package com.datn.finhome.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.datn.finhome.Models.ReviewModel;
import com.datn.finhome.Models.UserModel;
import com.datn.finhome.R;

import java.util.List;
import java.util.Objects;

public class DescriptionAdapter extends RecyclerView.Adapter<DescriptionAdapter.ViewHolder> {
    Context context;
    List<ReviewModel> list;
    List<UserModel> listUser;

    public DescriptionAdapter(Context context, List<ReviewModel> list, List<UserModel> listUser) {
        this.context = context;
        this.list = list;
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public DescriptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reviews, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DescriptionAdapter.ViewHolder holder, int position) {
        ReviewModel description = list.get(position);
        UserModel userModel = listUser.get(position);

//        if (Objects.equals(userModel.getUserID(), description.getIdUser())){
//            holder.tvNameUser.setText(userModel.getName());
//            Glide.with(context).load(userModel.getAvatar()).into(holder.imgUser);
//        }

        holder.tvDescription.setText(description.getReviews());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameUser, tvDescription;
        AppCompatImageView imgUser;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameUser = itemView.findViewById(R.id.tvUserNameReview);
            tvDescription = itemView.findViewById(R.id.tvAddressRoom);
            imgUser = itemView.findViewById(R.id.imgUserReview);
        }
    }
}
