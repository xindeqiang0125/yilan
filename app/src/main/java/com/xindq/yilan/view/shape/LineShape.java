package com.xindq.yilan.view.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class LineShape extends Shape {
    public static final int RELATIVE_START = 1;
    public static final int RELATIVE_END = 2;

    private float length = 0;
    private Point start = new Point(0, 0);
    private Point end = new Point(0, 0);

    public LineShape(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.length = Util.distance(start, end);
        updateBorder();
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint paint = getPaint();
        canvas.drawLine(start.x, start.y, end.x, end.y, paint);
    }

    @Override
    public void updateBorder() {
        setBorderLeft(Math.min(start.x, end.x));
        setBorderTop(Math.min(start.y, end.y));
        setBorderRight(Math.max(start.x, end.x));
        setBorderBottom(Math.max(start.y, end.y));
        if (getBorderRight() - getBorderLeft() < 20) {
            setBorderLeft((getBorderLeft() + getBorderRight()) / 2 - 10);
            setBorderRight((getBorderLeft() + getBorderRight()) / 2 + 10);
        }
        if (getBorderBottom() - getBorderTop() < 20) {
            setBorderTop((getBorderTop() + getBorderBottom()) / 2 - 10);
            setBorderBottom((getBorderTop() + getBorderBottom()) / 2 + 10);
        }
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length, int relative) {
        if (relative == RELATIVE_START) {
            Util.scale(start, end, length / this.length);
        } else if (relative == RELATIVE_END) {
            Util.scale(end, start, length / this.length);
        }
        this.length = length;
        updateBorder();
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
        this.length = Util.distance(start, end);
        updateBorder();
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
        this.length = Util.distance(start, end);
        updateBorder();
    }

    @Override
    public void setLineWidth(int lineWidth) {
        super.setLineWidth(lineWidth);
        getPaint().setStrokeWidth(lineWidth);
    }

    @Override
    public void setLineColor(int lineColor) {
        super.setLineColor(lineColor);
        getPaint().setColor(lineColor);
    }
}
