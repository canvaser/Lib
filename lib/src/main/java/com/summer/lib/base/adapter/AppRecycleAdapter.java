package com.summer.lib.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.summer.lib.R;

import butterknife.OnClick;

/**
 * Created by ${viwmox} on 2016-08-29.
 */
public abstract class AppRecycleAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> implements View.OnClickListener {

    protected Context context;

    protected LayoutInflater layoutInflater;

    View.OnClickListener onClickListener;

    public AppRecycleAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        holder.itemView.setTag(R.id.position,position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onClickListener.onClick(v);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
