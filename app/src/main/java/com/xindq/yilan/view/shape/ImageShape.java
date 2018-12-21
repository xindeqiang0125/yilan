package com.xindq.yilan.view.shape;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Base64;

import com.xindq.yilan.view.config.Action;

public class ImageShape extends Shape {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isHMirror;
    private boolean isVMirror;

    public ImageShape(String base64Image) {
        this.bitmap = stringToBitmap(base64Image);
    }

    public ImageShape setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        updateBorder();
        return this;
    }

    public ImageShape setSize(int width, int height) {
        this.width = width;
        this.height = height;
        updateBorder();
        return this;
    }

    public ImageShape setMirror(boolean h, boolean v) {
        this.isHMirror = h;
        this.isVMirror = v;
        Matrix matrix = new Matrix();
        if (this.isVMirror) matrix.postScale(1, -1);//镜像垂直翻转
        if (this.isHMirror) matrix.postScale(-1, 1);//镜像水平翻转
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return this;
    }

    private Bitmap stringToBitmap(String string) {
        //先去掉开头"data:image/png;base64,"
        string = string.substring(string.indexOf(",") + 1);
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void onDraw(Canvas canvas) {
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bitmap, null, dst, getPaint());
    }

    @Override
    public void updateBorder() {
        setBorderLeft(x);
        setBorderRight(x + width);
        setBorderTop(y);
        setBorderBottom(y + height);
    }

    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
        updateBorder();
    }

    @Override
    public void excuteAction(int type, Object value, int option) {
        super.excuteAction(type, value, option);
        switch (type) {
            case Action.ANGLE:
                setRadian((float) (((Float) value) / 180 * Math.PI));
                break;
        }
    }
}
