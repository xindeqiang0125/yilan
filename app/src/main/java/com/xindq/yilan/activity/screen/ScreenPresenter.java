package com.xindq.yilan.activity.screen;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xindq.yilan.view.config.Config;
import com.xindq.yilan.view.shape.CircleShape;
import com.xindq.yilan.view.shape.LineArrowShape;
import com.xindq.yilan.view.shape.LineShape;
import com.xindq.yilan.view.shape.PolygonShape;
import com.xindq.yilan.view.shape.RectangleShape;
import com.xindq.yilan.view.shape.Shape;
import com.xindq.yilan.view.shape.ShapeGroup;
import com.xindq.yilan.view.shape.TextShape;
import com.xindq.yilan.view.shape.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScreenPresenter implements DataCallback{
    private static final String TAG = "ScreenPresenter";

    private ScreenCallback callback;

    public ScreenPresenter(ScreenCallback callback) {
        this.callback = callback;
    }

    /**
     * 获取实时数据请求
     */
    public void requestdatas(Set<String> requestItems){
        Log.i(TAG, "requestdatas: "+requestItems);
        //用websocket
//        String serverURI = "ws://115.159.33.231:8181";
        String serverURI = "ws://115.159.33.231:8888/datas";
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
    public void requestShapesAndConfigs(){
        String url="http://115.159.33.231:8888/files/2/content";
        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String string = response.body().string();
                FileDecoder fileDecoder = new FileDecoder(string);

                List<Shape> shapeList = fileDecoder.decodeShapes();
                List<Config> configs = fileDecoder.decodeConfigs();
                if (callback != null) {
                    callback.onDecode(shapeList,configs);
                }
            }
        });
    }
}
