package com.xindq.yilan.view.shape;

import android.graphics.Point;

public class Util {
    public static void scale(Point zero,Point point,float scale){
        point.x= (int) ((point.x-zero.x)*scale+zero.x);
        point.y= (int) ((point.y-zero.y)*scale+zero.y);
    }
}
