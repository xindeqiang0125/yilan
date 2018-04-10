package com.xindq.yilan.view.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.Iterator;
import java.util.Vector;

/**
 * Created by XINDQ on 2018/4/10.
 */

public abstract class Shape {
    private static final String TAG = "Shape";
    /**
     * Shape大小，位置
     */
    private int x;
    private int y;
    private int width;
    private int height;

    /**
     * 顺时针倾斜弧度
     */
    private double radian = 0;
    /**
     * Shape是否显示
     */
    private boolean show = true;
    /**
     * Shape是否闪烁
     */
    private boolean twinkle = false;

    /**
     * 画笔
     */
    private Paint paint = new Paint();

    public Shape() {
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * 绘制图形
     *
     * @param canvas
     */
    public void draw(Canvas canvas) {
        if (isShow()) {
            canvas.save();
            canvas.rotate((float) getDegree(), getX() + getWidth() / 2, getY() + getHeight() / 2);
            onDraw(canvas);
            canvas.restore();
        }
    }

    public abstract void onDraw(Canvas canvas);

    //<editor-fold desc="getter and setter">


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isTwinkle() {
        return twinkle;
    }

    public void setTwinkle(boolean twinkle) {
        this.twinkle = twinkle;
    }

    public double getRadian() {
        return radian;
    }

    public double getDegree() {
        return radian * 180 / Math.PI;
    }

    public void setRadian(double radian) {
        this.radian = radian;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
    //</editor-fold>

}
