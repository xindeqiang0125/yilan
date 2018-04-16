package com.xindq.yilan.view.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.xindq.yilan.view.config.Action;

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
        if (getBorderRight() - getBorderLeft() < 40) {
            setBorderLeft((getBorderLeft() + getBorderRight()) / 2 - 20);
            setBorderRight((getBorderLeft() + getBorderRight()) / 2 + 20);
        }
        if (getBorderBottom() - getBorderTop() < 40) {
            setBorderTop((getBorderTop() + getBorderBottom()) / 2 - 10);
            setBorderBottom((getBorderTop() + getBorderBottom()) / 2 + 10);
        }
    }

    @Override
    public void move(int dx, int dy) {
        start.set(start.x + dx, start.y + dy);
        end.set(end.x + dx, end.y + dy);
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
        switch (type){
            case Action.LENGTH:
                setLength((Float) value,option);
                break;
            case Action.START_X:
                setStart(new Point(((Float) value).intValue(),getStart().y));
                break;
            case Action.START_Y:
                setStart(new Point(getStart().x,((Float) value).intValue()));
                break;
            case Action.END_X:
                setEnd(new Point(((Float) value).intValue(),getEnd().y));
                break;
            case Action.END_Y:
                setEnd(new Point(getEnd().x,((Float) value).intValue()));
                break;
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
