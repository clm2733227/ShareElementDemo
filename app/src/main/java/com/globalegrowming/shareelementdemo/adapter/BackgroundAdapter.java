package com.globalegrowming.shareelementdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.globalegrowming.shareelementdemo.GlideApp;
import com.globalegrowming.shareelementdemo.R;
import com.globalegrowming.shareelementdemo.bean.BackgroundBean;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * 作者：Create on 2017/5/31 17:07 by Rayming
 * 邮箱：clm2733227@163.com
 * 描述：TODO
 * 最近修改：2017/5/31 17:07 modify by Rayming
 */
public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.ViewHolder> implements View.OnClickListener {
    private List<String> data = new ArrayList<>();
    private Context context;
    private onItemClickListener onItemClickListener;

    public interface onItemClickListener {
        void onClick(int position, View view);
    }

    public BackgroundAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.back_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        GlideApp.with(context)
                .load(data.get(position))
                .placeholder(R.mipmap.ic_launcher_round)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onClick((Integer) v.getTag(), v);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

    public void setData(List<String> list) {
        data.clear();
        data.addAll(list);
    }


    public void setOnItemClickListener(BackgroundAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
