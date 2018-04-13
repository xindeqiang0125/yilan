package com.xindq.yilan.view.shape;

import android.graphics.Point;

import com.xindq.yilan.view.config.Action;

import java.util.List;

public class RectangleShape extends PolygonShape {
    /**
     * 设置宽高相对位置
     */
    public static final int RELATIVE_LEFT = 1;
    public static final int RELATIVE_RIGHT = 2;
    public static final int RELATIVE_TOP = 3;
    public static final int RELATIVE_BOTTOM = 4;
    /**
     * 宽高
     */
    private float width;
    private float height;

    public RectangleShape(List<Point> points) {
        super(points);
        updateWidthAndHeight();
    }

    private void updateWidthAndHeight() {
        List<Point> points = getPoints();
        int x0 = points.get(0).x;
        int y0 = points.get(0).y;
        int x1 = points.get(1).x;
        int y1 = points.get(1).y;
        int x2 = points.get(2).x;
        int y2 = points.get(2).y;
        this.width = (float) Math.sqrt(Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2));
        this.height = (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
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
            case Action.WIDTH:
                setWidth((float) value,option);
                break;
            case Action.HEIGHT:
                setHeight((float) value,option);
                break;
        }
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width, int relative) {
        if (relative == RELATIVE_LEFT) {
            Util.scale(getPoint(0), getPoint(1), width / this.width);
            Util.scale(getPoint(3), getPoint(2), width / this.width);
        } else if (relative == RELATIVE_RIGHT) {
            Util.scale(getPoint(1), getPoint(0), width / this.width);
            Util.scale(getPoint(2), getPoint(3), width / this.width);
        }
        this.width = width;
        updateBorder();
        updatePath();
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height, int relative) {
        if (relative == RELATIVE_TOP) {
            Util.scale(getPoint(0), getPoint(3), height / this.height);
            Util.scale(getPoint(1), getPoint(2), height / this.height);
        } else if (relative == RELATIVE_BOTTOM) {
            Util.scale(getPoint(3), getPoint(0), height / this.height);
            Util.scale(getPoint(2), getPoint(1), height / this.height);
        }
        this.height = height;
        updateBorder();
        updatePath();
    }
}
