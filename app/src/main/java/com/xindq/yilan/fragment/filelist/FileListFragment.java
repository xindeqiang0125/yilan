package com.xindq.yilan.fragment.filelist;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.xindq.yilan.R;
import com.xindq.yilan.activity.screen.ScreenActivity;
import com.xindq.yilan.domain.FileDetail;

import org.angmarch.views.NiceSpinner;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

public class FileListFragment extends Fragment implements FileListPresenter.CallBack, AdapterView.OnItemClickListener {
    private static final String TAG = "FileListFragment";
    private NiceSpinner spinner;
    private ListView listView;
    private FileListPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_list, container, false);
        spinner = view.findViewById(R.id.sp_family);
        listView = view.findViewById(R.id.lv_file);
        presenter = new FileListPresenter(getActivity(),this);
        presenter.requestFamilys();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = ((TextView) view).getText().toString();
                    String encode = URLEncoder.encode(text, "utf-8");
                    presenter.searchFiles(encode);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void familysReceived(List<String> familys) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinner.attachDataSource(familys);
                try {
                    String text = spinner.getText().toString();
                    String encode = URLEncoder.encode(text, "utf-8");
                    presenter.searchFiles(encode);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void filesReceived(List<FileDetail> files) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FileListViewAdapter adapter = new FileListViewAdapter(getActivity(), files);
                listView.setAdapter(adapter);
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FileListViewAdapter adapter = (FileListViewAdapter) parent.getAdapter();
        FileDetail detail = (FileDetail) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), ScreenActivity.class);
        intent.putExtra("fileId",detail.getId());
        getActivity().startActivity(intent);
    }
}
