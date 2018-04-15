package com.xindq.yilan.activity.screen;

import com.xindq.yilan.view.config.Config;
import com.xindq.yilan.view.shape.Shape;

import java.util.List;
import java.util.Map;

public interface ScreenCallback {
    void onDecode(List<Shape> shapes, List<Config> configs);
    void onReceiveDatas(Map<String,String> datas);
}
