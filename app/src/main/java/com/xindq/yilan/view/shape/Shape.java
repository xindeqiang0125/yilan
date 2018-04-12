package com.xindq.yilan.view.shape;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;


public abstract class Shape {
    private static final String TAG = "Shape";

    private int id;
    /**
     * Shape大小，位置
     */
    private int borderLeft;
    private int borderRight;
    private int borderBottom;
    private int borderTop;

    /**
     * 顺时针倾斜弧度
     */
    private float radian = 0;
    /**
     * Shape是否显示
     */
    private boolean show = true;
    /**
     * Shape是否闪烁
     */
    private boolean twinkle = false;

    /**
     * 样式
     */
    private int lineWidth = 4;
    private int lineColor = Color.BLACK;
    private int fillColor = Color.WHITE;

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
            canvas.rotate(getDegree(), (getBorderLeft() + getBorderRight()) / 2, (getBorderTop() + getBorderBottom()) / 2);
            onDraw(canvas);
            canvas.restore();
            drawBorder(canvas);
        }
    }

    protected void drawBorder(Canvas canvas){
        getPaint().setStyle(Paint.Style.STROKE);
        RectF rectF=new RectF(getBorderLeft(),getBorderTop(),getBorderRight(),getBorderBottom());
        canvas.drawRect(rectF,getPaint());
    }

    public abstract void onDraw(Canvas canvas);

    /**
     * 更新边界
     */
    public abstract void updateBorder();

    //<editor-fold desc="getter and setter">

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBorderLeft() {
        return borderLeft;
    }

    public void setBorderLeft(int borderLeft) {
        this.borderLeft = borderLeft;
    }

    public int getBorderRight() {
        return borderRight;
    }

    public void setBorderRight(int borderRight) {
        this.borderRight = borderRight;
    }

    public int getBorderBottom() {
        return borderBottom;
    }

    public void setBorderBottom(int borderBottom) {
        this.borderBottom = borderBottom;
    }

    public int getBorderTop() {
        return borderTop;
    }

    public void setBorderTop(int borderTop) {
        this.borderTop = borderTop;
    }

    public int getBorderWidth() {
        return getBorderRight()-getBorderLeft();
    }

    public int getBorderHeight(){
        return getBorderBottom()-getBorderTop();
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

    public float getRadian() {
        return radian;
    }

    public float getDegree() {
        return (float) (radian * 180 / Math.PI);
    }

    public void setRadian(float radian) {
        this.radian = radian;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

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
