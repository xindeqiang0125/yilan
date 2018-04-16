package com.xindq.yilan.util;

import android.graphics.Color;
import android.util.*;

public class ColorUtil {
    public static int parseColor(String color){
        try {
            if (color.contains("rgba")){
                color=color.substring(color.indexOf('(')+1,color.indexOf(')'));
                String[] split = color.split(",");
                int a= (int) (Float.parseFloat(split[3])*255);
                int r=Integer.parseInt(split[0]);
                int g=Integer.parseInt(split[1]);
                int b=Integer.parseInt(split[2]);
                return Color.argb(a,r,g,b);
            }else if (color.contains("#")){
                return Color.parseColor(color.replace("#","#ff"));
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }
}
