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
        for (int i = 0; i < shapes.size(); i++) {
            Shape shape = shapes.get(i);
            if (i==0){
                setBorderLeft(shape.getBorderLeft());
                setBorderRight(shape.getBorderRight());
                setBorderTop(shape.getBorderTop());
                setBorderBottom(shape.getBorderBottom());
            }else {
                setBorderLeft(Math.min(shape.getBorderLeft(), getBorderLeft()));
                setBorderTop(Math.min(shape.getBorderTop(), getBorderTop()));
                setBorderRight(Math.max(shape.getBorderRight(), getBorderRight()));
                setBorderBottom(Math.max(shape.getBorderBottom(), getBorderBottom()));
            }
        }
    }

    public void addShape(Shape shape){
        shapes.add(shape);
        updateBorder();
    }
}
