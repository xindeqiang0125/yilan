package com.xindq.yilan.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void showShort(Context context, CharSequence message) {
        Toast mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setText(message);
        mToast.show();
    }

    public static void showLong(Context context, CharSequence message) {
        Toast mToast = Toast.makeText(context, null, Toast.LENGTH_LONG);
        mToast.setText(message);
        mToast.show();
    }
}
