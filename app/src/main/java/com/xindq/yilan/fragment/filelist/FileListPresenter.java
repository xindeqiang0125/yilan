package com.xindq.yilan.fragment.filelist;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xindq.yilan.R;
import com.xindq.yilan.domain.FileDetail;
import com.xindq.yilan.web.HttpClient;

import java.util.List;

public class FileListPresenter {
    private static final String TAG = "FileListPresenter";
    private Context context;
    private CallBack callBack;

    public FileListPresenter(Context context, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
    }

    public void searchFiles(String family){
        String url = context.getString(R.string.file_search_url) + "?family=" + family;
        HttpClient.getInstance().get(url, new HttpClient.OnHttpResponse() {
            @Override
            public void onHttpResponse(String reponse) {
                List<FileDetail> list = JSON.parseArray(reponse, FileDetail.class);
                callBack.filesReceived(list);
            }
        });
    }

    public void requestFamilys(){
        String url = context.getString(R.string.family_list_url);
        HttpClient.getInstance().get(url, new HttpClient.OnHttpResponse() {
            @Override
            public void onHttpResponse(String reponse) {
                List<String> list = JSON.parseArray(reponse, String.class);
                callBack.familysReceived(list);
            }
        });
    }

    interface CallBack{
        void familysReceived(List<String> familys);
        void filesReceived(List<FileDetail> files);
    }
}
