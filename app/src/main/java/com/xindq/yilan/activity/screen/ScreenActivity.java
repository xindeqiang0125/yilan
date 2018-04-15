package com.xindq.yilan.activity.screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xindq.yilan.R;
import com.xindq.yilan.view.ScreenView;
import com.xindq.yilan.view.config.Config;
import com.xindq.yilan.view.shape.Shape;

import java.util.List;
import java.util.Map;

public class ScreenActivity extends AppCompatActivity implements ScreenCallback{
    private ScreenView screenView;
    private List<Config> configs;
    private ScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        screenView=findViewById(R.id.screenView);
        presenter=new ScreenPresenter(this);
        presenter.requestShapesAndConfigs();
    }

    @Override
    public void onDecode(List<Shape> shapes, List<Config> configs) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Shape shape : shapes) {
                    screenView.addShape(shape);
                }
                ScreenActivity.this.configs=configs;
            }
        });

    }

    @Override
    public void onReceiveDatas(Map<String, String> datas) {
        for (Config config : configs) {
            config.startUp(datas);
        }
    }
}
