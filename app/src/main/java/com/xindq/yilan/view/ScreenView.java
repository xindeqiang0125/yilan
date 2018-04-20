package com.xindq.yilan.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
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
    private List<Shape> shapes = new ArrayList<>();
    private OnLongClickShape onLongClickShape;
    private Timer timer = new Timer();

    public ScreenView(Context context) {
        super(context);
    }

    public ScreenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScreenView);
        int time = ta.getInteger(R.styleable.ScreenView_refreshTime, 50);
        setRefreshTime(time);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = getShapesRight() + getShapesLeft();
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, widthSize);
            }
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getShapesTop() + getShapesBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }
        }
        setMeasuredDimension(width, height);
    }

    private int getShapesRight() {
        if (shapes.size() == 0) return 0;
        int value = shapes.get(0).getBorderRight();
        for (Shape shape : shapes) {
            value = Math.max(value, shape.getBorderRight());
        }
        return value;
    }

    private int getShapesBottom() {
        if (shapes.size() == 0) return 0;
        int value = shapes.get(0).getBorderBottom();
        for (Shape shape : shapes) {
            value = Math.max(value, shape.getBorderBottom());
        }
        return value;
    }

    private int getShapesLeft() {
        if (shapes.size() == 0) return 0;
        int value = shapes.get(0).getBorderLeft();
        for (Shape shape : shapes) {
            value = Math.min(value, shape.getBorderLeft());
        }
        return value;
    }

    private int getShapesTop() {
        if (shapes.size() == 0) return 0;
        int value = shapes.get(0).getBorderTop();
        for (Shape shape : shapes) {
            value = Math.min(value, shape.getBorderTop());
        }
        return value;
    }

    public void setOnLongClickShape(OnLongClickShape onLongClickShape) {
        this.onLongClickShape = onLongClickShape;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long s = System.currentTimeMillis();
        for (Shape shape : shapes) {
            shape.draw(canvas);
        }
        long e = System.currentTimeMillis();
//        Log.i(TAG, "onDraw: time:"+(e-s));
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

    public void setRefreshTime(int refreshTime) {
        this.refreshTime = refreshTime;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ScreenView.this.repaint();
            }
        }, 1000, this.refreshTime);
    }

    private int downX = 0, downY = 0, upX = 0, upY = 0;
    private long downTime = 0, upTime = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                downTime = event.getEventTime();
                break;
            case MotionEvent.ACTION_UP:
                upX = (int) event.getX();
                upY = (int) event.getY();
                upTime = event.getEventTime();
                if (Math.abs(downX - upX) < 10 && Math.abs(downY - upY) < 10
                        && upTime - downTime > 1000) {
                    onLongClick(upX, upY);
                }
                break;
        }
        return true;
    }

    private void onLongClick(int x, int y) {
        Shape shape = getLongClickedShape(x, y);
        if (onLongClickShape != null && shape != null) {
            onLongClickShape.onLongClickShape(shape);
        }
    }

    private Shape getLongClickedShape(int x, int y) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (shape.getBorderLeft() < x && shape.getBorderRight() > x &&
                    shape.getBorderTop() < y && shape.getBorderBottom() > y) {
                return shape;
            }
        }
        return null;
    }


    /**
     * 点击图形回调接口
     */
    public interface OnLongClickShape {
        void onLongClickShape(Shape shape);
    }

}
