package com.xindq.yilan.view.config;

import com.xindq.yilan.view.shape.Shape;

public class Config {
    private Shape shape;
    private Condition condition;
    private Action action;

    public Config(Shape shape, Condition condition, Action action) {
        this.shape = shape;
        this.condition = condition;
        this.action = action;
    }
}
