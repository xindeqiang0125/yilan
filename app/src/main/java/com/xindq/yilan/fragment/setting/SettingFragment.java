package com.xindq.yilan.fragment.setting;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.xindq.yilan.R;
import com.xindq.yilan.activity.login.LoginActivity;
import com.xindq.yilan.util.SPStorage;
import com.xindq.yilan.util.ToastUtil;
import com.xindq.yilan.web.HttpResult;

public class SettingFragment extends Fragment implements View.OnClickListener, SettingPresenter.CallBack {
    private EditText etAppServerAddr;
    private EditText etRealDataAddr;
    private EditText etHistoryDataAddr;
    private EditText etPassword;
    private EditText etName;
    private Button saveSetting;
    private Button logout;

    private SettingPresenter presenter;
    private SPStorage loginStorage;
    private SPStorage serverStorage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(view);
        initData();
        presenter = new SettingPresenter(getActivity(),this);
        return view;
    }

    private void initData() {
        loginStorage = new SPStorage(getActivity(),"login");
        serverStorage = new SPStorage(getActivity(),"server");
        etAppServerAddr.setText(serverStorage.getString("app_server_addr"));
        etRealDataAddr.setText(serverStorage.getString("real_data_addr"));
        etHistoryDataAddr.setText(serverStorage.getString("history_data_addr"));
        etName.setText(loginStorage.getString("name"));
        etPassword.setText(loginStorage.getString("password"));
    }

    private void initView(View view) {
        etAppServerAddr = view.findViewById(R.id.app_server_addr);
        etRealDataAddr = view.findViewById(R.id.real_data_addr);
        etHistoryDataAddr = view.findViewById(R.id.history_data_addr);
        etPassword = view.findViewById(R.id.change_password);
        etName = view.findViewById(R.id.change_name);
        saveSetting = view.findViewById(R.id.btn_save_setting);
        logout = view.findViewById(R.id.btn_logout);
        saveSetting.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                loginStorage.putBoolean("autoLogin",false);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                break;
            case R.id.btn_save_setting:
                saveData();
                presenter.saveUser(
                        loginStorage.getString("phone"),
                        etName.getText().toString(),
                        etPassword.getText().toString());
                break;
        }

    }

    private void saveData() {
        serverStorage.putString("app_server_addr",etAppServerAddr.getText().toString());
        serverStorage.putString("real_data_addr",etRealDataAddr.getText().toString());
        serverStorage.putString("history_data_addr",etHistoryDataAddr.getText().toString());
        loginStorage.putString("name",etName.getText().toString());
        loginStorage.putString("password",etPassword.getText().toString());
    }

    @Override
    public void userSaved(HttpResult result) {
        Activity activity = getActivity();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShort(activity,result.getMsg().toString());
            }
        });
    }
}
