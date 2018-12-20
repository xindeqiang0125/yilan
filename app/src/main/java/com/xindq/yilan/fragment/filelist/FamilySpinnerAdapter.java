package com.xindq.yilan.fragment.filelist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xindq.yilan.domain.FileDetail;

import java.util.List;

public class FamilySpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<String> familys;

    public FamilySpinnerAdapter(Context context, List<String> familys) {
        this.context = context;
        this.familys = familys;
    }

    @Override
    public int getCount() {
        return familys.size();
    }

    @Override
    public Object getItem(int position) {
        return familys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
