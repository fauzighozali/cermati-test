package com.fauzighozali.cermatitest.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.fauzighozali.cermatitest.R;
import com.fauzighozali.cermatitest.model.Items;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private List<Items> items;
    private Context context;

    public MainAdapter(List<Items> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(items.get(position).getName());
        Glide.with(context)
                .load(items.get(position).getAvatar())
                .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_github)
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView avatar;
        MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_view_name);
            avatar = itemView.findViewById(R.id.image_view_avatar);
        }
    }
}
