package com.xindq.yilan.activity.screen;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xindq.yilan.R;
import com.xindq.yilan.view.ScreenView;
import com.xindq.yilan.view.config.Config;
import com.xindq.yilan.view.shape.Shape;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScreenActivity extends AppCompatActivity implements ScreenCallback{
    private static final String TAG = "ScreenActivity";
    private List<Config> configs;
    private ScreenPresenter presenter;
    private LinearLayout screenContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        screenContainer = findViewById(R.id.screen_container);
        presenter=new ScreenPresenter(this);
//        String url="http://115.159.33.231:8888/files/"+2+"/content";
        String url="http://115.159.33.231:8888/files/"+getIntent().getIntExtra("fileId",1)+"/content";
        presenter.requestShapesAndConfigs(url);
    }

    /**
     * 解析图形组态完成回调函数
     * @param shapes
     * @param configs
     */
    @Override
    public void onDecode(List<Shape> shapes, List<Config> configs) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //创建ScreenView，并初始化
                ScreenView screenView=new ScreenView(ScreenActivity.this);
                screenView.setRefreshTime(100);
                screenView.setBackgroundColor(Color.parseColor("#660000ff"));
                //LayoutParams，并初始化
                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(20,20,20,20);

                //将获取的图形加入screenView
                for (Shape shape : shapes) {
                    screenView.addShape(shape);
                }
                //将screenView加入screenContainer
                screenContainer.addView(screenView, params);
                ScreenActivity.this.configs=configs;
            }
        });
        Set<String> requestItems=new HashSet<>();
        for (Config config : configs){
            requestItems.addAll(config.getRequestItems());
        }
        presenter.requestdatas(requestItems);
    }

    /**
     * 接受到实时数据回调函数
     * @param datas
     */
    @Override
    public void onReceiveDatas(Map<String, String> datas) {
//        Log.i(TAG, "onReceiveDatas: "+datas);
//        long s=System.currentTimeMillis();

        for (Config config : configs) {
            config.startUp(datas);
        }
//        long e=System.currentTimeMillis();
//        Log.i(TAG, "onReceiveDatas: "+(e-s));
    }
}
