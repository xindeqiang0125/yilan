package com.xindq.yilan.view.shape;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void scale() {
        double degrees = Math.toDegrees(Math.atan(5/0.0f));
        System.out.println(degrees);
        System.out.println(Math.sin(Math.atan(5/0.0f)));
        System.out.println(Math.cos(Math.atan(5/0.0f))*20);
//        System.out.println(Double.POSITIVE_INFINITY);
    }
}