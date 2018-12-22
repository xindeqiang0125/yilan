package com.xindq.yilan.fragment.filelist;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xindq.yilan.R;
import com.xindq.yilan.domain.FileDetail;
import com.xindq.yilan.util.SPStorage;
import com.xindq.yilan.util.UrlUtil;
import com.xindq.yilan.web.HttpClient;

import java.util.List;

public class FileListPresenter {
    private static final String TAG = "FileListPresenter";
    private Context context;
    private CallBack callBack;
    private SPStorage serverStorage;

    public FileListPresenter(Context context, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.serverStorage = new SPStorage(context, "server");
    }

    public void searchFiles(String family){
        String serverAddr = serverStorage.getString("app_server_addr");
        String path = context.getString(R.string.file_search_url);
        String url = UrlUtil.getHttpUrl(serverAddr, path) + "?family=" + family;
        HttpClient.getInstance().get(url, new HttpClient.OnHttpResponse() {
            @Override
            public void onHttpResponse(String reponse) {
                List<FileDetail> list = JSON.parseArray(reponse, FileDetail.class);
                callBack.filesReceived(list);
            }
        });
    }

    public void requestFamilys(){
        String serverAddr = serverStorage.getString("app_server_addr");
        String path = context.getString(R.string.family_list_url);
        String url = UrlUtil.getHttpUrl(serverAddr, path);
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
