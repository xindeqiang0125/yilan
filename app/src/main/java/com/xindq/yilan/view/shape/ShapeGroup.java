package com.xindq.yilan.view.shape;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class ShapeGroup extends Shape {

    private List<Shape> shapes=new ArrayList<>();

    @Override
    public void onDraw(Canvas canvas) {
        for (Shape shape : shapes) {
            shape.draw(canvas);
        }
    }

    @Override
    public void updateBorder() {

    }
}
