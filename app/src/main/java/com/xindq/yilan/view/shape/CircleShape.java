package com.xindq.yilan.view.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;

import com.xindq.yilan.view.config.Action;

public class CircleShape extends Shape {
    private static final String TAG = "CircleShape";
    /**
     * a,b,圆心
     */
    private int a;
    private int b;
    private Point center;

    public CircleShape(int a, int b, Point center, float radian) {
        this.a = a;
        this.b = b;
        this.center = center;
        setRadian(radian);
        updateBorder();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.translate(center.x, center.y);
        Paint paint = getPaint();
        paint.setStrokeWidth(getLineWidth());
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getFillColor());
        RectF rectF = new RectF(-a, -b, a, b);
        canvas.drawOval(rectF, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getLineColor());
        canvas.drawOval(rectF, paint);
    }

    @Override
    public void updateBorder() {
        setBorderLeft(center.x - a);
        setBorderRight(center.x + a);
        setBorderTop(center.y - b);
        setBorderBottom(center.y + b);
    }

    @Override
    public void move(int dx, int dy) {
        center.set(center.x + dx, center.y + dy);
        updateBorder();
    }

    /**
     * 对本shape执行动态效果
     *
     * @param type   动态效果类型，见Action中定义。
     * @param value  实时值
     * @param option 附加参数 见每种Shape中定义的常量（不是每种Shape都有）。
     */
    @Override
    public void excuteAction(int type, Object value, int option) {
        super.excuteAction(type, value, option);
        switch (type) {
            case Action.A:
                setA(((Float) value).intValue());
                break;
            case Action.B:
                setB(((Float) value).intValue());
                break;
            case Action.RADIUS:
                setRadius(((Float) value).intValue());
                break;
            case Action.ANGLE:
                setRadian((float) (((Float) value) / 180 * Math.PI));
                break;
        }
    }

    //<editor-fold desc="getter and setter">
    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
        updateBorder();
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
        updateBorder();
    }

    public void setRadius(int radius) {
        setA(radius);
        setB(radius);
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
        updateBorder();
    }

    public void setCenter(int x, int y) {
        setCenter(new Point(x, y));
    }
    //</editor-fold>
}
