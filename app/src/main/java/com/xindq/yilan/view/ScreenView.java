package com.xindq.yilan.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xindq.yilan.R;
import com.xindq.yilan.view.shape.Shape;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by XINDQ on 2018/4/10.
 */

public class ScreenView extends View implements OnRepaint {
    private static final String TAG = "ScreenView";

    private int refreshTime;
    List<Shape> shapes = new ArrayList<>();
    private Timer timer = new Timer();

    public ScreenView(Context context) {
        this(context, null);
    }

    public ScreenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScreenView);
        this.refreshTime = ta.getInteger(R.styleable.ScreenView_refreshTime, 50);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ScreenView.this.repaint();
            }
        }, 1000, this.refreshTime);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width , height;
        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else {
            width = getShapeRight() + getShapeLeft();
            if (widthMode == MeasureSpec.AT_MOST){
                width=Math.min(width,widthSize);
            }
        }
        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = getShapeTop() + getShapeBottom();
            if (heightMode == MeasureSpec.AT_MOST){
                height=Math.min(height,heightSize);
            }
        }
        setMeasuredDimension(width, height);
    }

    private int getShapeRight() {
        if (shapes.size() == 0) return 0;
        int value = shapes.get(0).getX() + shapes.get(0).getWidth();
        for (Shape shape : shapes) {
            value = Math.max(value, shape.getX() + shape.getWidth());
        }
        return value;
    }

    private int getShapeBottom() {
        if (shapes.size() == 0) return 0;
        int value = shapes.get(0).getY() + shapes.get(0).getHeight();
        for (Shape shape : shapes) {
            value = Math.max(value, shape.getY() + shape.getHeight());
        }
        return value;
    }

    private int getShapeLeft() {
        if (shapes.size() == 0) return 0;
        int value = shapes.get(0).getX();
        for (Shape shape : shapes) {
            value = Math.min(value, shape.getX());
        }
        return value;
    }

    private int getShapeTop() {
        if (shapes.size() == 0) return 0;
        int value = shapes.get(0).getY();
        for (Shape shape : shapes) {
            value = Math.min(value, shape.getY());
        }
        return value;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long s = System.currentTimeMillis();
        for (Shape shape : shapes) {
            shape.draw(canvas);
        }
        long e = System.currentTimeMillis();
        Log.i(TAG, "onDraw: time:"+(e-s));
    }

    /**
     * 添加图形
     *
     * @param shape
     */
    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    @Override
    public void repaint() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            this.invalidate();
        });
//        Log.i(TAG, "repaint: ");
    }
}
