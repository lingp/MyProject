package com.lin.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.lin.framework.BuildConfig;

/**
 * 数据存储
 */
public class SpUtils {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private volatile static SpUtils instance;

    private SpUtils() {

    }

    public static SpUtils getInstance() {
        if (instance == null) {
            synchronized (SpUtils.class) {
                if (instance == null) {
                    instance = new SpUtils();
                }
            }
        }
        return instance;
    }

    public void initSp(Context context) {
        /**
         * MODE_PRIVATE：只限于本应用读写
         * MODE_WORLD_READABLE:支持其他应用读，但是不能写
         * MODE_WORLD_WRITEABLE:其他应用可以写
         */
        sp = context.getSharedPreferences(BuildConfig.SP_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void putInt(String key, int values) {
        editor.putInt(key, values);
        editor.commit();
    }

    public int getInt(String key, int defValues) {
        return sp.getInt(key, defValues);
    }

    public void putString(String key, String values) {
        editor.putString(key, values);
        editor.commit();
    }

    public String getString(String key, String defValues) {
        return sp.getString(key, defValues);
    }

    public void putBoolean(String key, boolean values) {
        editor.putBoolean(key, values);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defValues) {
        return sp.getBoolean(key, defValues);
    }

    public void deleteKey(String key) {
        editor.remove(key);
        editor.commit();
    }

}
