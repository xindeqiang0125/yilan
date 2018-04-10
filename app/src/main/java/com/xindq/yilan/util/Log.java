package com.xindq.yilan.util;

public class Log {
    private static final String TAG = "Log";
    private static final boolean DEBUG=true;

    public static void i(String s){
        if (DEBUG)
            android.util.Log.i(TAG, s);
    }
}
