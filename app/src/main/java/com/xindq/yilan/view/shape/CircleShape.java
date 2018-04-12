package com.xindq.yilan.view.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

public class CircleShape extends Shape {
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
