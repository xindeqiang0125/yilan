package com.xindq.yilan.activity.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xindq.yilan.R;
import com.xindq.yilan.domain.FileDetail;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListVH> implements View.OnClickListener {
    private static final String TAG = "ListAdapter";

    private List<FileDetail> list = new ArrayList<>();
    private OnClickItemListener listener;

    public ListAdapter(List<FileDetail> list, OnClickItemListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_list, null);
        return new ListVH(v);
    }

    @Override
    public void onBindViewHolder(ListVH holder, int position) {
        FileDetail fileDetail = list.get(position);
        holder.nameTextView.setText(fileDetail.getName());
        holder.detailTextView.setText(fileDetail.getDetail());


        holder.view.setTag(list.get(position));

        holder.view.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        listener.onClickItem((FileDetail) v.getTag());
    }

    class ListVH extends RecyclerView.ViewHolder {
        View view;
        ImageView imageView;
        TextView nameTextView;
        TextView detailTextView;

        public ListVH(View itemView) {
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.image);
            nameTextView = itemView.findViewById(R.id.name);
            detailTextView = itemView.findViewById(R.id.detail);
        }
    }

    interface OnClickItemListener {
        void onClickItem(FileDetail detail);
    }
}
