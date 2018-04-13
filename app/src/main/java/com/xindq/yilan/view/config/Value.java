package com.xindq.yilan.view.config;

public class Value {
    private float floatValue;
    private boolean booleanValue;

    public Value(String v) {
        v = v.trim();
        try {
            floatValue = Float.parseFloat(v);
        } catch (NumberFormatException e) {
            booleanValue = Boolean.parseBoolean(v);
        }
    }

    public int intValue() {
        return (int) floatValue;
    }

    public float floatValue() {
        return floatValue;
    }

    public double doubleValue() {
        return floatValue;
    }

    public boolean booleanValue() {
        return booleanValue;
    }

    public static Value parse(String v) {
        return new Value(v);
    }
}
