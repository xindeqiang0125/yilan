package com.xindq.yilan.view.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by XINDQ on 2018/4/10.
 */

public class CircleShape extends RectangleShape {
    /**
     * 半径,长轴,短轴
     */
    private int radius;
    private int a;
    private int b;

    @Override
    public void onDraw(Canvas canvas) {
        Paint paint = getPaint();
        paint.setStrokeWidth(getLineWidth());
        RectF oval = new RectF(getX(), getY(), getX() + getWidth(), getY() + getHeight());

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getFillColor());
        canvas.drawOval(oval,paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getLineColor());
        canvas.drawOval(oval,paint);
    }
}
