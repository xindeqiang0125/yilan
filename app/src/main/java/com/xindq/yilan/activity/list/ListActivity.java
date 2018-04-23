package com.xindq.yilan.activity.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xindq.yilan.R;
import com.xindq.yilan.activity.screen.ScreenActivity;
import com.xindq.yilan.domain.FileDetail;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements OnReceiveDataLisenter, ListAdapter.OnClickItemListener {

    private RecyclerView fileList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        fileList = findViewById(R.id.fileList);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fileList.setLayoutManager(layoutManager);

        ListPresenter presenter = new ListPresenter(this,this);
        presenter.requestFiles();
    }

    @Override
    public void receiveData(List<FileDetail> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter=new ListAdapter(list,ListActivity.this);
                fileList.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onClickItem(FileDetail detail) {
        Intent intent=new Intent(this, ScreenActivity.class);
        intent.putExtra("fileId",detail.getId());
        startActivity(intent);
    }
}
