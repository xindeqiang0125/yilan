package com.xindq.yilan.fragment.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xindq.yilan.R;
import com.xindq.yilan.domain.Item;

import java.util.List;

public class ItemListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Item> items;

    public ItemListViewAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder();
            holder.idView = convertView.findViewById(R.id.item_list_id);
            holder.nameView = convertView.findViewById(R.id.item_list_name);
            holder.notesView = convertView.findViewById(R.id.item_list_notes);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Item item = items.get(position);
        holder.idView.setText(""+item.getId());
        holder.nameView.setText(item.getItemName());
        holder.notesView.setText(item.getNotes());
        return convertView;
    }

    private class ViewHolder {
        TextView idView;
        TextView nameView;
        TextView notesView;
    }
}
