package com.xindq.yilan.view.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

public class LineArrowShape extends LineShape {
    private Path path = new Path();
    private Point p1;
    private Point p2;
    private Point p3;

    public LineArrowShape(Point start, Point end) {
        super(start, end);
        getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        initPath();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path,getPaint());
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
        p1.set(p1.x + dx, p1.y + dy);
        p2.set(p2.x + dx, p2.y + dy);
        p3.set(p3.x + dx, p3.y + dy);
    }

    @Override
    public void setLength(float length, int relative) {
        super.setLength(length, relative);
        initPath();
    }

    @Override
    public void setStart(Point start) {
        super.setStart(start);
        initPath();
    }

    @Override
    public void setEnd(Point end) {
        super.setEnd(end);
        initPath();
    }

    @Override
    public void setLineWidth(int lineWidth) {
        super.setLineWidth(lineWidth);
        initPath();
    }

    private void initPath(){
        float r= (float) Math.atan2(getEnd().y-getStart().y,getEnd().x-getStart().x);
        int number1 = Math.max(5,getLineWidth());
        int number2 = 2*number1;
        p1=new Point((int)(getEnd().x+number1*Math.sin(r)),
                (int)(getEnd().y-number1*Math.cos(r)));
        p2=new Point(2*getEnd().x-p1.x,
                2*getEnd().y-p1.y);
        p3=new Point((int)(getEnd().x+(getEnd().x-getStart().x)*number2/getLength()),
                (int)(getEnd().y+(getEnd().y-getStart().y)*number2/getLength()));
        path.reset();
        path.moveTo(p1.x,p1.y);
        path.lineTo(p2.x,p2.y);
        path.lineTo(p3.x,p3.y);
        path.close();
    }
}
