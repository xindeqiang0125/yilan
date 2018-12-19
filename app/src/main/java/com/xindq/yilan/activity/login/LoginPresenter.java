package com.xindq.yilan.activity.login;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xindq.yilan.R;
import com.xindq.yilan.web.HttpClient;
import com.xindq.yilan.web.HttpResult;

public class LoginPresenter {
    private static final String TAG = "LoginPresenter";
    private CallBack callBack;

    public LoginPresenter(CallBack callBack) {
        this.callBack = callBack;
    }

    public void login(String phone, String password){
        String url = ((Context)callBack).getString(R.string.login_url) + phone + "&password=" + password;
        HttpClient.getInstance().get(url, new HttpClient.OnHttpResponse() {
            @Override
            public void onHttpResponse(String reponse) {
                HttpResult result = JSON.parseObject(reponse, HttpResult.class);
                callBack.loginCallBack(result);
            }
        });
    }

    interface CallBack{
        void loginCallBack(HttpResult result);
    }
}
