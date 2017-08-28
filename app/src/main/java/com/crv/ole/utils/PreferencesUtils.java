package com.crv.ole.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.crv.ole.BaseApplication;

import java.util.Map;

/**
 * sherepreferce操作工具类
 * 默认文件名为包名
 * 字符串类型加密存储
 */
public class PreferencesUtils {
    public final static float FLOAT_DEFAULT = 0;
    public final static String STRING_DEFAULT = "";
    public final static int INT_DEFAULT = 0;
    public final static boolean BOOLEAN_DEFAULT = false;

    private volatile static PreferencesUtils mPreferencesUtils;

    private SharedPreferences mSharedPreferences;
    private Editor mEditor;

    private PreferencesUtils() {
        mSharedPreferences = BaseApplication.getInstance().getSharedPreferences(BaseApplication.getInstance().getPackageName(), Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static PreferencesUtils getInstance() {
        if (mPreferencesUtils == null) {
            synchronized (PreferencesUtils.class) {
                if (mPreferencesUtils == null) {
                    mPreferencesUtils = new PreferencesUtils();
                }
            }
        }
        return mPreferencesUtils;
    }

    /**
     * put
     *
     * @param key
     * @param value
     */
    public void put(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public void put(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public void put(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    public void put(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public void put(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, STRING_DEFAULT);
    }

    public String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }


    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, BOOLEAN_DEFAULT);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public float getFloat(String key) {
        return mSharedPreferences.getFloat(key, FLOAT_DEFAULT);
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, INT_DEFAULT);
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, INT_DEFAULT);
    }

    public int getInt(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public Map<String, ?> getAll() {
        return mSharedPreferences.getAll();
    }

    public boolean isContains(String key) {
        return mSharedPreferences.contains(key);
    }

    public void remove(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    public void clear() {
        mEditor.clear().commit();
    }

    public final void setSharedPreferences(SharedPreferences preferences) {
        mSharedPreferences = preferences;
        mEditor = mSharedPreferences.edit();
    }

}
