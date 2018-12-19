package com.xindq.yilan.activity.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.xindq.yilan.activity.main.MainActivity;
import com.xindq.yilan.R;
import com.xindq.yilan.util.SPStorage;
import com.xindq.yilan.util.ToastUtil;
import com.xindq.yilan.web.HttpResult;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginPresenter.CallBack {
    private static final String TAG = "LoginActivity";

    private EditText etPhone;
    private EditText etPassword;
    private Button btnLogin;
    private CheckBox cbRememberMe;
    private CheckBox cbAutoLogin;

    private String phone;
    private String password;
    private boolean rememberMe;
    private boolean autoLogin;

    private SPStorage spStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spStorage = new SPStorage(this, "login");
        getInfo();
        checkStorage();
    }

    private void checkStorage() {
        if (autoLogin) {
            LoginPresenter loginPresenter = new LoginPresenter(this);
            loginPresenter.login(phone, password);
        } else {
            init();
        }
    }

    private void init() {
        etPhone = findViewById(R.id.loginTel);
        etPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.loginBtn);
        cbRememberMe = findViewById(R.id.rememberMe);
        cbAutoLogin = findViewById(R.id.autoLogin);
        btnLogin.setOnClickListener(this);
        etPhone.setText(phone);
        etPassword.setText(password);
        cbRememberMe.setChecked(rememberMe);
        cbAutoLogin.setChecked(autoLogin);
    }

    private void getInfo() {
        phone = spStorage.getString("phone");
        password = spStorage.getString("password");
        rememberMe = spStorage.getBoolean("rememberMe");
        autoLogin = spStorage.getBoolean("autoLogin");
    }

    @Override
    public void onClick(View v) {
        phone = etPhone.getText().toString();
        password = this.etPassword.getText().toString();
        rememberMe = cbRememberMe.isChecked();
        autoLogin = cbAutoLogin.isChecked();
        saveInfo(phone, password, rememberMe, autoLogin);
        LoginPresenter loginPresenter = new LoginPresenter(this);
        loginPresenter.login(phone, password);
    }

    private void saveInfo(String phone, String password, boolean rememberMe, boolean autoLogin) {
        spStorage.putString("phone", rememberMe ? phone : "");
        spStorage.putString("password", rememberMe ? password : "");
        spStorage.putBoolean("rememberMe", rememberMe);
        spStorage.putBoolean("autoLogin", autoLogin && rememberMe);
    }

    @Override
    public void loginCallBack(HttpResult result) {
        JSONObject msg = (JSONObject) result.getMsg();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result.getResult().equals("成功")) {
                    spStorage.putString("name", msg.getString("name"));
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                } else {
                    ToastUtil.showShort(LoginActivity.this, "用户或密码错误");
                }

            }
        });
    }
}
