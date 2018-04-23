package com.xindq.yilan.activity.screen;

import android.content.Context;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xindq.yilan.R;
import com.xindq.yilan.view.config.Config;
import com.xindq.yilan.view.shape.Shape;
import com.xindq.yilan.web.DatasClient;
import com.xindq.yilan.web.HttpClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScreenPresenter implements DataCallback{
    private static final String TAG = "ScreenPresenter";

    private ScreenCallback callback;
    private Context context;

    public ScreenPresenter(Context context,ScreenCallback callback) {
        this.callback = callback;
        this.context = context;
    }

    /**
     * 获取实时数据请求
     */
    public void requestdatas(Set<String> requestItems){
        //用websocket
//        String serverURI = "ws://115.159.33.231:8181";
        String serverURI = context.getString(R.string.real_data_url);
        DatasClient client=new DatasClient(serverURI,requestItems);
        client.setCallback(this);
        client.connect();
    }

    /**
     * 获取实时数据回调
     * @param datas
     */
    @Override
    public void onReceiveDatas(Map<String, String> datas) {
        if (callback != null) {
            callback.onReceiveDatas(datas);
        }
    }

    /**
     * 获取Shapes和Configs
     */
    public void requestShapesAndConfigs(String url){
        HttpClient.getInstance().get(url, new HttpClient.OnHttpResponse() {
            @Override
            public void onHttpResponse(String reponse) {
                FileDecoder fileDecoder = new FileDecoder(reponse);

                List<Shape> shapeList = fileDecoder.decodeShapes();
                List<Config> configs = fileDecoder.decodeConfigs();
                if (callback != null) {
                    callback.onDecode(shapeList,configs);
                }
            }
        });
    }
}
