package com.xindq.yilan.activity.screen;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.xindq.yilan.R;

import java.util.Map;

public class ConfigDialog {
    private Context context;
    private AlertDialog.Builder builder;

    private Spinner spinner;
    private AlertDialog dialog;

    public ConfigDialog(Context context) {
        this.context = context;
        init();
    }

    private void init(){
        builder=new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.shape_item_info, null);
        spinner=view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(view);
        dialog = builder.create();
    }

    public void setDatas(Map<String, String> datas){

    }

    public void show(){
        dialog.show();
    }

    public void close(){
        dialog.cancel();
    }

    public Spinner getSpinner() {
        return spinner;
    }
}
