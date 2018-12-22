package com.xindq.yilan.view.shape;

import android.graphics.Point;

public class Util {
    public static void scale(Point zero, Point point, float newLength) {
        double radian = Math.atan((point.y - zero.y) / ((point.x - zero.x) * 1.0));
        point.x = (int) (newLength * Math.cos(radian) + zero.x);
        point.y = (int) (newLength * Math.sin(radian) + zero.y);
    }

    public static float distance(Point a, Point b) {
        double x = Math.pow(a.x - b.x, 2);
        double y = Math.pow(a.y - b.y, 2);
        return (float) Math.sqrt(x + y);
    }
}
