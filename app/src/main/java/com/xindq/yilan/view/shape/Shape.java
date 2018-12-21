package com.xindq.yilan.view.shape;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.xindq.yilan.view.config.Action;


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
     * 由于闪烁，记录之前的显示状态
     */
    private Boolean oldShow;

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

    /**
     * 执行闪烁的线程
     */
    private Thread twinkleThread;

    public Shape() {
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * 绘制图形模板方法
     * @param canvas
     */
    public void draw(Canvas canvas) {
        if (isShow()) {
            canvas.save();
            canvas.rotate(getDegree(), (getBorderLeft() + getBorderRight()) / 2, (getBorderTop() + getBorderBottom()) / 2);
            onDraw(canvas);
            canvas.restore();
//            drawBorder(canvas);
        }
    }

    /**
     * 绘制图形边界
     * @param canvas
     */
    protected void drawBorder(Canvas canvas){
        getPaint().setStyle(Paint.Style.STROKE);
        RectF rectF=new RectF(getBorderLeft(),getBorderTop(),getBorderRight(),getBorderBottom());
        canvas.drawRect(rectF,getPaint());
    }

    /**
     * 绘制图形抽象方法，由子类具体实现
     * @param canvas
     */
    public abstract void onDraw(Canvas canvas);

    /**
     * 更新边界，由子类具体实现
     */
    public abstract void updateBorder();

    /**
     * 对本shape执行动态效果
     * @param type 动态效果类型，见Action中定义。
     * @param value 实时值
     * @param option 附加参数 见每种Shape中定义的常量（不是每种Shape都有）。
     */
    public void excuteAction(int type,Object value,int option){
        switch (type){
            case Action.POSITION_X:
                move(((Float) value).intValue()-getBorderLeft(),0);
                break;
            case Action.POSITION_Y:
                move(0, ((Float) value).intValue()-getBorderTop());
                break;
            case Action.RADIAN:
                setRadian(((Float) value));
                break;
            case Action.LINE_COLOR:
                setLineColor((Integer) value);
                break;
            case Action.FILL_COLOR:
                setFillColor((Integer) value);
                break;
            case Action.LINE_WIDTH:
                setLineWidth(((Float) value).intValue());
                break;
        }
    }

    /**
     * 平移图形
     * @param dx
     * @param dy
     */
    public abstract void move(int dx,int dy);

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
        if (twinkle){
            if (oldShow==null) oldShow=isShow();
            if (twinkleThread == null) {
                twinkleThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        enableTwinkle();
                    }
                });
                twinkleThread.start();
            }
        }
    }

    /**
     * 启动闪烁
     */
    private void enableTwinkle() {
        while (this.twinkle){
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.setShow(!isShow());
        }
        twinkleThread=null;
        setShow(oldShow);
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
