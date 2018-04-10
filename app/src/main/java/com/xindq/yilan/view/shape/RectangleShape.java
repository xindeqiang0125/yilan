package com.xindq.yilan.view.shape;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class RectangleShape extends Shape{
    private int lineWidth=4;
    private int lineColor=Color.BLACK;
    private int fillColor=Color.WHITE;

    @Override
    public void onDraw(Canvas canvas) {
        Rect rect = new Rect(getX(), getY(), getX() + getWidth(), getY() + getHeight());

        Paint paint = getPaint();
        paint.setStrokeWidth(lineWidth);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(fillColor);
        canvas.drawRect(rect,paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(lineColor);
        canvas.drawRect(rect,paint);
    }

    //<editor-fold desc="getter and setter">
    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }
    //</editor-fold>
}
