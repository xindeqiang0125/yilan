package com.xindq.yilan.fragment.setting;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xindq.yilan.R;
import com.xindq.yilan.util.SPStorage;
import com.xindq.yilan.web.HttpClient;
import com.xindq.yilan.web.HttpResult;

public class SettingPresenter {
    private static final String TAG = "SettingPresenter";

    private CallBack callBack;
    private Context context;

    public SettingPresenter(Context context, CallBack callBack) {
        this.callBack = callBack;
        this.context = context;
    }

    public void saveUser(String phone, String name, String password) {
        String url = context.getString(R.string.update_user_url) + "?tel=" + phone + "&password=" + password + "&name=" + name;
        HttpClient.getInstance().get(url, new HttpClient.OnHttpResponse() {
            @Override
            public void onHttpResponse(String reponse) {
                HttpResult result = JSON.parseObject(reponse, HttpResult.class);
                callBack.userSaved(result);
            }
        });
    }

    interface CallBack {
        void userSaved(HttpResult result);
    }
}
