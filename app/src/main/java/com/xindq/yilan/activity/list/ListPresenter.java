package com.xindq.yilan.activity.list;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xindq.yilan.domain.FileDetail;

import java.io.IOException;
import java.util.List;

public class ListPresenter {
    private static final String TAG = "ListPresenter";

    private OnReceiveDataLisenter lisenter;

    public ListPresenter(OnReceiveDataLisenter lisenter) {
        this.lisenter = lisenter;
    }

    public void requestFiles(){
        OkHttpClient client=new OkHttpClient();
        Request.Builder builder=new Request.Builder();
        Request request = builder.url("http://115.159.33.231:8888/files/all")
                .get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                List<FileDetail> list = JSON.parseArray(response.body().string(), FileDetail.class);
                if (lisenter != null) {
                    lisenter.receiveData(list);
                }
            }
        });
    }
}
