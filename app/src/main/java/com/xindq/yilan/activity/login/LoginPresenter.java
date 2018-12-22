package com.xindq.yilan.activity.login;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xindq.yilan.R;
import com.xindq.yilan.util.SPStorage;
import com.xindq.yilan.util.UrlUtil;
import com.xindq.yilan.web.HttpClient;
import com.xindq.yilan.web.HttpResult;

public class LoginPresenter {
    private static final String TAG = "LoginPresenter";
    private Context context;
    private CallBack callBack;

    public LoginPresenter(Context context, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
    }

    public void login(String server, String phone, String password) {
        String path = context.getString(R.string.login_url);
        String url = UrlUtil.getHttpUrl(server, path) + "?telephone=" + phone + "&password=" + password;
        HttpClient.getInstance().get(url, new HttpClient.OnHttpResponse() {
            @Override
            public void onHttpResponse(String reponse) {
                HttpResult result = JSON.parseObject(reponse, HttpResult.class);
                callBack.loginCallBack(result);
            }
        });
    }

    interface CallBack {
        void loginCallBack(HttpResult result);
    }
}
