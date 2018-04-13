package com.xindq.yilan.view.config;

import com.xindq.yilan.view.shape.Shape;

import java.util.Map;

public class Action {
    /**
     * shape 可以的组态,子类能继承使用
     */
    public static final int POSITION_X   = 1;
    public static final int POSITION_Y   = 2;
    public static final int RADIAN       = 3;
    public static final int HIDE         = 4;
    public static final int TWINKLE      = 5;
    public static final int LINE_COLOR   = 6;
    public static final int FILL_COLOR   = 7;
    public static final int LINE_WIDTH   = 8;
    /**
     * ShapeGroup,PolygonShape 没有自身组态
     */
    /**
     * RectangleShape可以的组态
     */
    public static final int WIDTH        = 9;
    public static final int HEIGHT       = 10;

    /**
     * CircleShape可以的组态
     */
    public static final int A            = 11;
    public static final int B            = 12;
    public static final int RADIUS       = 13;

    /**
     * TextShape可以的组态
     */
    public static final int TEXTS        = 14;
    public static final int TEXT_COLOR   = 15;
    public static final int BACKGROUND_COLOR   = 16;
    public static final int TEXT_SIZE    = 17;

    /**
     * LineShape,LineArrowShape可以的组态
     */
    public static final int LENGTH       = 18;
    public static final int START_X      = 19;
    public static final int START_Y      = 20;
    public static final int END_X        = 21;
    public static final int END_Y        = 22;

    /**
     * 该action绑定的shape
     */
    private Shape shape;
    /**
     * 该action关联的实时数据测点
     */
    private String item;

    /**
     * 构造函数
     * @param shape
     * @param item
     */
    public Action(Shape shape, String item) {
        this.shape = shape;
        this.item = item;
    }

    /**
     * 执行动作
     * @param datas
     */
    public void excute(Map<String, String> datas) {
        String value = datas.get(item);
//        shape.excuteAction(type,value,option);
    }
}
