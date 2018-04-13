package com.xindq.yilan.view.config;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ConditionTest {
    @Test
    public void infixToSuffix() throws Exception {
        Map<String,String> datas=new HashMap<>();
        datas.put("0","5");
        datas.put("1","8");
        datas.put("2","16");
        datas.put("3","23");
        Condition condition = new Condition("{0} = 5 and ({2} < 10 or {1} > 9)");
        System.out.println(condition.result(datas));
        System.out.println(condition.getCondition());
    }

    @Test
    public void result() throws Exception {
//        int maxValue = Integer.MAX_VALUE;
//        System.out.println(maxValue);
//        float f=maxValue;
//        System.out.println((int)f);
        try {
            System.out.println(Float.parseFloat("57"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

}