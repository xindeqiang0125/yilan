package com.xindq.yilan.view.config;

import android.util.Log;

import com.xindq.yilan.util.ColorUtil;
import com.xindq.yilan.view.shape.CircleShape;
import com.xindq.yilan.view.shape.LineArrowShape;
import com.xindq.yilan.view.shape.LineShape;
import com.xindq.yilan.view.shape.RectangleShape;
import com.xindq.yilan.view.shape.Shape;
import com.xindq.yilan.view.shape.TextShape;

import java.util.Map;

public class Action {
    private static final String TAG = "Action";
    /**
     * shape 可以的组态,子类能继承使用
     */
    public static final int POSITION_X = 1;
    public static final int POSITION_Y = 2;
    public static final int RADIAN = 3;
    public static final int HIDE = 4;
    public static final int TWINKLE = 5;
    public static final int LINE_COLOR = 6;
    public static final int FILL_COLOR = 7;
    public static final int LINE_WIDTH = 8;
    /**
     * ShapeGroup,PolygonShape 没有自身组态
     */
    /**
     * RectangleShape可以的组态
     */
    public static final int WIDTH = 9;
    public static final int HEIGHT = 10;

    /**
     * CircleShape可以的组态
     */
    public static final int A = 11;
    public static final int B = 12;
    public static final int RADIUS = 13;

    /**
     * TextShape可以的组态
     */
    public static final int TEXTS = 14;
    public static final int TEXT_COLOR = 15;
    public static final int BACKGROUND_COLOR = 16;
    public static final int TEXT_SIZE = 17;

    /**
     * LineShape,LineArrowShape可以的组态
     */
    public static final int LENGTH = 18;
    public static final int START_X = 19;
    public static final int START_Y = 20;
    public static final int END_X = 21;
    public static final int END_Y = 22;

    /**
     * 该action绑定的shape
     */
    private Shape shape;
    /**
     * 该action关联的实时数据测点
     */
    private String item;

    /**
     * 组态类型，取值为本类中定义的常量
     */
    private int type;

    /**
     * 附加参数
     */
    private int option = -1000;

    /**
     * 实时值与图形显示值的缩放比例，如实时值为100，图形宽度为200，scale=2(斜率)
     */
    private float scaleA = 1;

    /**
     * 截距
     */
    private float scaleB = 0;

    /**
     * 组态值为固定，而不是根据测点获取
     */
    private String value;
    /**
     * 记录数据变化前的原始值，用于条件不满足时恢复
     */
    private Object oldValue;

    /**
     * 构造函数(没有item，代表只能是HIDE或TWINKLE)
     *
     * @param shape
     * @param type
     */
    public Action(Shape shape, int type) {
        this.shape = shape;
        this.type = type;
    }

    /**
     * 构造函数
     *
     * @param shape
     * @param item
     * @param type
     */
    public Action(Shape shape, String item, int type) {
        this.shape = shape;
        this.item = item;
        this.type = type;
    }

    /**
     * 构造函数
     *
     * @param shape
     * @param item
     * @param type
     * @param option
     */
    public Action(Shape shape, String item, int type, int option) {
        this.shape = shape;
        this.item = item;
        this.type = type;
        this.option = option;
    }

    public Action(Shape shape, String s) {
        this.shape = shape;
        s = s.toLowerCase();
        s = s.replace(" ", "");
        if ("hide".equals(s)) {
            type = Action.HIDE;
        } else if ("twinkle".equals(s)) {
            type = Action.TWINKLE;
        } else {
            String[] split = s.split("=");
            String action = split[0];
            if ("position.x".equals(action)) {
                type = Action.POSITION_X;
            } else if ("position.y".equals(action)) {
                type = Action.POSITION_Y;
            } else if ("radian".equals(action)) {
                type = Action.RADIAN;
            } else if ("linecolor".equals(action)) {
                type = Action.LINE_COLOR;
            } else if ("fillcolor".equals(action)) {
                type = Action.FILL_COLOR;
            } else if ("linewidth".equals(action)) {
                type = Action.LINE_WIDTH;
            } else if ("width.left".equals(action)) {
                type = Action.WIDTH;
                option = RectangleShape.RELATIVE_LEFT;
            } else if ("width.right".equals(action)) {
                type = Action.WIDTH;
                option = RectangleShape.RELATIVE_RIGHT;
            } else if ("height.top".equals(action)) {
                type = Action.HEIGHT;
                option = RectangleShape.RELATIVE_TOP;
            } else if ("height.bottom".equals(action)) {
                type = Action.HEIGHT;
                option = RectangleShape.RELATIVE_BOTTOM;
            } else if ("a".equals(action)) {
                type = Action.A;
            } else if ("b".equals(action)) {
                type = Action.B;
            } else if ("radius".equals(action)) {
                type = Action.RADIUS;
            } else if ("text".equals(action)) {
                type = Action.TEXTS;
            } else if ("textcolor".equals(action)) {
                type = Action.TEXT_COLOR;
            } else if ("textsize".equals(action)) {
                type = Action.TEXT_SIZE;
            } else if ("backgroundcolor".equals(action)) {
                type = Action.BACKGROUND_COLOR;
            } else if ("linelength.start".equals(action)) {
                type = Action.LENGTH;
                option = LineShape.RELATIVE_START;
            } else if ("linelength.end".equals(action)) {
                type = Action.LENGTH;
                option = LineShape.RELATIVE_END;
            } else if ("start.x".equals(action)) {
                type = Action.START_X;
            } else if ("start.y".equals(action)) {
                type = Action.START_Y;
            } else if ("end.x".equals(action)) {
                type = Action.END_X;
            } else if ("end.y".equals(action)) {
                type = Action.END_Y;
            }
            //获取测点
            if (split[1].contains("{")) {
                item = split[1].substring(split[1].indexOf('{') + 1, split[1].indexOf('}'));
            } else {
                value = split[1];
            }
            //获取缩放系数
            if (split[1].contains("[")) {
                String nums = split[1].substring(split[1].indexOf('[') + 1, split[1].indexOf(']'));
                String[] strings = nums.split("[;:]");
                float x1 = Float.parseFloat(strings[0]);
                float y1 = Float.parseFloat(strings[1]);
                float x2 = Float.parseFloat(strings[2]);
                float y2 = Float.parseFloat(strings[3]);
                this.scaleA = (y2 - y1) / (x2 - x1);
                this.scaleB = y1 - this.scaleA * x1;
            }

        }
    }

    /**
     * 执行动作
     *
     * @param datas  实时数据
     * @param enable 是否使能
     */
    public void excute(Map<String, String> datas, boolean enable) {
        if (type == HIDE) {
            shape.setShow(!enable);
        } else if (type == TWINKLE) {
            shape.setTwinkle(enable);
        }else {
            if (enable) {
                Object value = getValue(datas, item);
                if (type == Action.TEXT_COLOR || type == Action.BACKGROUND_COLOR ||
                        type == Action.FILL_COLOR || type == Action.LINE_COLOR) {
                    value = ColorUtil.parseColor((String) value);
                } else {
                    try {
                        float data = Float.parseFloat((String) value);
                        /**
                         * data还要根据取值范围缩放
                         */
                        data = data * scaleA + scaleB;
                        value = data;
                    } catch (NumberFormatException e) {
                        value = ((String) value).split("\n");
                    }
                }
                recordOldValue();
                shape.excuteAction(type, value, option);
            } else {
                //回复原始值
                shape.excuteAction(type, oldValue, option);
            }
        }

    }

    /**
     * 记录组态未启动时原始值
     */
    private void recordOldValue(){
        if (oldValue == null) {
            switch (type) {
                case Action.A:
                    oldValue = ((CircleShape) shape).getA();
                    break;
                case Action.B:
                    oldValue = ((CircleShape) shape).getB();
                    break;
                case Action.BACKGROUND_COLOR:
                    oldValue = ((TextShape) shape).getBackgroundColor();
                    break;
                case Action.END_X:
                    oldValue = ((LineShape) shape).getEnd().x;
                    break;
                case Action.END_Y:
                    oldValue = ((LineShape) shape).getEnd().y;
                    break;
                case Action.FILL_COLOR:
                    oldValue = shape.getFillColor();
                    break;
                case Action.HEIGHT:
                    oldValue = ((RectangleShape) shape).getHeight();
                    break;
                case Action.HIDE:
                    oldValue = !(shape).isShow();
                    break;
                case Action.LENGTH:
                    oldValue = ((LineShape) shape).getLength();
                    break;
                case Action.LINE_COLOR:
                    oldValue = (shape).getLineColor();
                    break;
                case Action.LINE_WIDTH:
                    oldValue = (shape).getLineWidth();
                    break;
                case Action.POSITION_X:
                    oldValue = shape.getBorderLeft();
                    break;
                case Action.POSITION_Y:
                    oldValue = shape.getBorderTop();
                    break;
                case Action.RADIAN:
                    oldValue = shape.getRadian();
                    break;
                case Action.RADIUS:
                    oldValue = ((CircleShape) shape).getA();
                    break;
                case Action.START_X:
                    oldValue = ((LineShape) shape).getStart().x;
                    break;
                case Action.START_Y:
                    oldValue = ((LineShape) shape).getStart().y;
                    break;
                case Action.TEXT_COLOR:
                    oldValue = ((TextShape) shape).getTextColor();
                    break;
                case Action.TEXT_SIZE:
                    oldValue = ((TextShape) shape).getTextSize();
                    break;
                case Action.TEXTS:
                    oldValue = ((TextShape) shape).getTexts();
                    break;
                case Action.TWINKLE:
                    oldValue = (shape).isTwinkle();
                    break;
                case Action.WIDTH:
                    oldValue = ((RectangleShape) shape).getWidth();
                    break;
            }
        }
    }

    private String getValue(Map<String, String> datas, String key) {
        if (this.value != null) {
            key = "unknown";
        }
        String s;
        if (datas.containsKey(key)) {
            s = datas.get(key);
        } else {
            s = this.value;
        }
        return s;
    }

    public String getItem() {
        return item;
    }
}
