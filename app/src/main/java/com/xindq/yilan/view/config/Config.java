package com.xindq.yilan.view.config;

import com.xindq.yilan.view.shape.Shape;

import java.util.Map;

public class Config {
    private Shape shape;
    private Condition condition;
    private Action action;

    public Config(Shape shape, Condition condition, Action action) {
        this.shape = shape;
        this.condition = condition;
        this.action = action;
    }

    /**
     * 根据实时数据启动组态动画
     * @param datas
     */
    public void startUp(Map<String, String> datas){
        boolean result = condition.result(datas);
        if (result){
            action.excute(datas);
        }
    }
}
