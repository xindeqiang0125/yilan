package com.xindq.yilan.fragment.search;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xindq.yilan.R;
import com.xindq.yilan.domain.Item;
import com.xindq.yilan.util.SPStorage;
import com.xindq.yilan.util.UrlUtil;
import com.xindq.yilan.web.HttpClient;

import java.util.List;

public class SearchPresenter {
    private static final String TAG = "SearchPresenter";
    private Context context;
    private CallBack callBack;
    private SPStorage serverStorage;

    public SearchPresenter(Context context, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.serverStorage = new SPStorage(context, "server");
    }

    public void searchItems(String name) {
        String serverAddr = serverStorage.getString("app_server_addr");
        String path = context.getString(R.string.search_item_url);
        String url = UrlUtil.getHttpUrl(serverAddr, path) + "?page=1&rows=20&itemName=" + name;
        HttpClient.getInstance().get(url, new HttpClient.OnHttpResponse() {
            @Override
            public void onHttpResponse(String reponse) {
                JSONObject jsonObject = JSON.parseObject(reponse);
                JSONArray rows = jsonObject.getJSONArray("rows");
                List<Item> items = JSON.parseArray(rows.toJSONString(), Item.class);
                for (Item item : items) {
                    String group = item.getGroup();
                    String groupName = JSON.parseObject(group).getString("groupName");
                    item.setGroup(groupName);
                }
                callBack.onItemsSearched(items);
            }
        });
    }

    interface CallBack {
        void onItemsSearched(List<Item> items);
    }
}
