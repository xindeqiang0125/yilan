package com.xindq.yilan.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPStorage {
    private Context context;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SPStorage(Context context, String name) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void putString(String key, String value) {
        editor.putString(key, value).apply();
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }
}
