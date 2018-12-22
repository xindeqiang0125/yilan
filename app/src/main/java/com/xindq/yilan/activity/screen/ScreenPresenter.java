package com.xindq.yilan.activity.screen;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xindq.yilan.R;
import com.xindq.yilan.domain.Item;
import com.xindq.yilan.util.SPStorage;
import com.xindq.yilan.util.UrlUtil;
import com.xindq.yilan.view.config.Config;
import com.xindq.yilan.view.shape.Shape;
import com.xindq.yilan.web.DatasClient;
import com.xindq.yilan.web.HttpClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScreenPresenter implements DataCallback {
    private static final String TAG = "ScreenPresenter";

    private ScreenCallback callback;
    private Context context;
    private DatasClient client;
    private SPStorage serverStorage;

    public ScreenPresenter(Context context, ScreenCallback callback) {
        this.callback = callback;
        this.context = context;
        this.serverStorage = new SPStorage(context, "server");
    }

    /**
     * 获取实时数据请求
     */
    public void requestdatas(Set<String> requestItems) {
        String serverAddr = serverStorage.getString("real_data_addr");
        String path = context.getString(R.string.real_data_url);
        String url = UrlUtil.getWsUrl(serverAddr, path);
        client = new DatasClient(url, requestItems);
        client.setCallback(this);
        client.connect();
    }

    public void closeDatasConnection(){
        client.close();
    }

    /**
     * 获取实时数据回调
     *
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
    public void requestShapesAndConfigs(int fileId) {
        String serverAddr = serverStorage.getString("app_server_addr");
        String path = context.getString(R.string.file_content_url);
        String url = UrlUtil.getHttpUrl(serverAddr, path) + "?id=" + fileId;
        HttpClient.getInstance().get(url, new HttpClient.OnHttpResponse() {
            @Override
            public void onHttpResponse(String reponse) {
                FileDecoder fileDecoder = new FileDecoder(reponse);

                List<Shape> shapeList = fileDecoder.decodeShapes();
                List<Config> configs = fileDecoder.decodeConfigs();
                if (callback != null) {
                    callback.onDecode(shapeList, configs);
                }
            }
        });
    }

    public void requestItem(int itemId){
        String serverAddr = serverStorage.getString("app_server_addr");
        String path = context.getString(R.string.get_item_url);
        String url = UrlUtil.getHttpUrl(serverAddr, path) + "?itemId=" + itemId;
        HttpClient.getInstance().get(url, new HttpClient.OnHttpResponse() {
            @Override
            public void onHttpResponse(String reponse) {
                Item item = JSON.parseObject(reponse, Item.class);
                JSONObject jsonObject = JSON.parseObject(item.getGroup());
                String groupName = jsonObject.getString("groupName");
                item.setGroup(groupName);
                if (callback != null) {
                    callback.onGetItem(item);
                }
            }
        });
    }
}
