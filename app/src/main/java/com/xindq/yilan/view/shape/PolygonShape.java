package com.xindq.yilan.view.shape;

import android.graphics.*;

import java.util.ArrayList;
import java.util.List;

public class PolygonShape extends Shape {
    private List<Point> points = new ArrayList<>();
    private Path path = new Path();

    public PolygonShape() {
    }

    public PolygonShape(List<Point> points) {
        this.points=points;
        updateBorder();
        updatePath();
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint paint = getPaint();
        paint.setStrokeWidth(getLineWidth());
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getFillColor());
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getLineColor());
        canvas.drawPath(path, paint);
    }

    /**
     * 更新路径
     */
    public void updatePath(){
        path.reset();
        for (int i = 0; i < points.size(); i++) {
            Point point=points.get(i);
            if (i==0) path.moveTo(point.x, point.y);
            else path.lineTo(point.x, point.y);
        }
        path.close();
    }
    /**
     * 添加节点时调整边框
     */
    @Override
    public void updateBorder() {
        for (int i = 0; i < points.size(); i++) {
            Point point=points.get(i);
            if (i==0){
                setBorderLeft(point.x);
                setBorderTop(point.y);
                setBorderRight(point.x);
                setBorderBottom(point.y);
            }else {
                setBorderLeft(Math.min(point.x, getBorderLeft()));
                setBorderTop(Math.min(point.y, getBorderTop()));
                setBorderRight(Math.max(point.x, getBorderRight()));
                setBorderBottom(Math.max(point.y, getBorderBottom()));
            }
        }
    }

    /**
     * 修改某一点的坐标
     * @param index 序号
     * @param point 新点
     */
    public void updatePoint(int index,Point point){
        Point p = points.get(index);
        p.set(point.x,point.y);
        updateBorder();
        updatePath();
    }

    public Point getPoint(int index){
        return points.get(index);
    }

    //<editor-fold desc="getter and setter">
    public List<Point> getPoints() {
        return points;
    }
    //</editor-fold>
}
