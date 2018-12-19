package com.xindq.yilan.fragment.filelist;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import com.xindq.yilan.R;

public class FileListFragment extends Fragment {
    private Spinner spinner;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_list, container, false);
        spinner = view.findViewById(R.id.sp_family);
        listView = view.findViewById(R.id.lv_file);
        return view;
    }
}
