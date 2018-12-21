package com.xindq.yilan.activity.history;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xindq.yilan.domain.HistoryEntry;
import com.xindq.yilan.web.HttpClient;

import java.util.ArrayList;
import java.util.List;

public class HistoryPresenter {
    private static final String TAG = "HistoryPresenter";
    private Context context;
    private Callback callback;

    public HistoryPresenter(Context context, Callback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void requsetHistory(int itemId, String start, String end){
        String url= "http://192.168.2.105/gethistory"+"?itemId="+itemId+"&start="+start+"&end="+end;
        HttpClient.getInstance().get(url, new HttpClient.OnHttpResponse() {
            @Override
            public void onHttpResponse(String reponse) {
                if (callback != null) {
                    List<HistoryEntry> historyEntries = JSON.parseArray(reponse, HistoryEntry.class);
                    callback.onGetHistory(historyEntries);
                }
            }
        });
    }

    interface Callback{
        void onGetHistory(List<HistoryEntry> historyEntries);
    }
}
