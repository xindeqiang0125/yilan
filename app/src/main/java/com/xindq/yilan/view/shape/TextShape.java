package com.xindq.yilan.view.shape;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.xindq.yilan.view.config.Action;

public class TextShape extends Shape {
    private static final String TAG = "TextShape";
    private String[] texts;
    private int backgroundColor = Color.GREEN;
    private int textColor = Color.RED;
    private int textSize = 50;
    private Point position = new Point(0,0);

    public TextShape(String[] texts) {
        this.texts = texts;
        getPaint().setTextSize(textSize);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint paint = getPaint();
        RectF rectF = new RectF(getBorderLeft(), getBorderTop(), getBorderRight(), getBorderBottom());
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(rectF, paint);

        paint.setTypeface(Typeface.DEFAULT);
        paint.setColor(textColor);
        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        for (int i = 0; i < texts.length; i++) {
            canvas.drawText(texts[i],
                    position.x,
                    position.y - fontMetrics.ascent +i * (fontMetrics.bottom-fontMetrics.top),
                    paint);
        }
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void updateBorder() {
        int w=0,h;
        for (String text : texts) {
            int textWidth = (int) getPaint().measureText(text);
            w=Math.max(w,textWidth);
        }
        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        h= (int) (texts.length*(fontMetrics.bottom-fontMetrics.top));
        setBorderLeft(position.x);
        setBorderTop(position.y);
        setBorderRight(position.x + w);
        setBorderBottom(position.y + h);
    }

    @Override
    public void move(int dx, int dy) {
        position.set(position.x+dx,position.y+dy);
        updateBorder();
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
            case Action.TEXTS:
                if (!(value instanceof String[])){
                    value=new String[]{value.toString()};
                }
                setTexts((String[]) value);
                break;
            case Action.FONT_COLOR:
                setTextColor((Integer) value);
                break;
            case Action.BACKGROUND_COLOR:
                setBackgroundColor((Integer) value);
                break;
            case Action.FONT_SIZE:
                setTextSize(((Float) value).intValue());
                break;
        }
    }

    /**
     * 获取文本宽度
     * @param paint
     * @param str
     * @return
     */
    private int getTextWidth(Paint paint, String str) {
        int w= 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                w+= (int) Math.ceil(widths[j]);
            }
        }
        return w;
    }

    public String[] getTexts() {
        return texts;
    }

    public void setTexts(String[] texts) {
        this.texts = texts;
        updateBorder();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        getPaint().setTextSize(textSize);
        updateBorder();
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
        updateBorder();
    }

    public void setPosition(int x, int y) {
        setPosition(new Point(x,y));
    }
}
