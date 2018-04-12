package com.xindq.yilan.view.shape;

import android.graphics.Point;

public class Util {
    public static void scale(Point zero, Point point, float scale) {
        point.x = (int) ((point.x - zero.x) * scale + zero.x);
        point.y = (int) ((point.y - zero.y) * scale + zero.y);
    }

    public static float distance(Point a, Point b) {
        double x = Math.pow(a.x - b.x, 2);
        double y = Math.pow(a.y - b.y, 2);
        return (float) Math.sqrt(x + y);
    }
}
