package com.xindq.yilan.activity.list;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xindq.yilan.R;
import com.xindq.yilan.domain.FileDetail;
import com.xindq.yilan.web.HttpClient;

import java.io.IOException;
import java.util.List;

public class ListPresenter {
    private static final String TAG = "ListPresenter";

    private OnReceiveDataLisenter lisenter;
    private Context context;

    public ListPresenter(Context context,OnReceiveDataLisenter lisenter) {
        this.lisenter = lisenter;
        this.context = context;
    }

    public void requestFiles(){
        HttpClient.getInstance().get(context.getString(R.string.shape_list_url), new HttpClient.OnHttpResponse() {
            @Override
            public void onHttpResponse(String reponse) {
                List<FileDetail> list = JSON.parseArray(reponse, FileDetail.class);
                if (lisenter != null) {
                    lisenter.receiveData(list);
                }
            }
        });
    }
}
