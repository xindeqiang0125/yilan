package com.xindq.yilan.fragment.filelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xindq.yilan.R;
import com.xindq.yilan.domain.FileDetail;
import com.xindq.yilan.domain.Item;

import java.util.List;

public class FileListViewAdapter extends BaseAdapter {
    private Context context;
    private List<FileDetail> files;

    public FileListViewAdapter(Context context, List<FileDetail> files) {
        this.context = context;
        this.files = files;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.file_list, parent, false);
            holder = new ViewHolder();
            holder.idView = convertView.findViewById(R.id.file_list_id);
            holder.nameView = convertView.findViewById(R.id.file_list_name);
            holder.notesView = convertView.findViewById(R.id.file_list_notes);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FileDetail file = files.get(position);
        holder.idView.setText(""+file.getId());
        holder.nameView.setText(file.getName());
        holder.notesView.setText(file.getDetail());
        return convertView;
    }

    private class ViewHolder {
        TextView idView;
        TextView nameView;
        TextView notesView;
    }
}
