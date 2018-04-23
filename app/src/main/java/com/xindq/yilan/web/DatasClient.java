package com.xindq.yilan.web;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xindq.yilan.activity.screen.DataCallback;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DatasClient extends WebSocketClient{
    private static final String TAG = "DatasClient";

    private Set<String> requestItems;
    private DataCallback callback;

    public DatasClient(URI serverURI,Set<String> requestItems) {
        super(serverURI);
        this.requestItems=requestItems;
    }

    public DatasClient(String serverURI,Set<String> requestItems) {
        this(URI.create(serverURI),requestItems);
    }

    /**
     * 设置监听器
     * @param callback
     */
    public void setCallback(DataCallback callback) {
        this.callback = callback;
    }

    /**
     * 链接打开后向服务器传送要获取的测点标识
     * @param handshakedata
     */
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        String s = JSON.toJSONString(requestItems);
        this.send(s);
    }

    @Override
    public void onMessage(String message) {
        Map<String,String> datas=new HashMap<>();
        try {
            datas=JSON.parseObject(message,new TypeReference<Map<String,String>>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (callback != null) {
            callback.onReceiveDatas(datas);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        this.send("?");
    }

    @Override
    public void onError(Exception ex) {

    }
}
