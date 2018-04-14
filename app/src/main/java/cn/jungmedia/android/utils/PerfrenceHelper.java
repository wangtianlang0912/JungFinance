package cn.jungmedia.android.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/17. 下午10:00
 *
 *
 */
public class PerfrenceHelper {
    private static String PREFERENCE_NAME = null;

    private PerfrenceHelper() {
    }

    public static void initialize(String name) {
        PREFERENCE_NAME = name;
    }

    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = getSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getString(Context context, String key) {
        return getString(context, key, (String)null);
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = getSharedPreferences(context);
        return settings.getString(key, defaultValue);
    }

    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences settings = getSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences settings = getSharedPreferences(context);
        return settings.getInt(key, defaultValue);
    }

    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences settings = getSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static long getLong(Context context, String key) {
        return getLong(context, key, -1L);
    }

    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = getSharedPreferences(context);
        return settings.getLong(key, defaultValue);
    }

    public static boolean putFloat(Context context, String key, float value) {
        SharedPreferences settings = getSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1.0F);
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences settings = getSharedPreferences(context);
        return settings.getFloat(key, defaultValue);
    }

    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = getSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = getSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        checkInit(context);
        return context.getSharedPreferences(PREFERENCE_NAME, 0);
    }

    private static void checkInit(Context context) {
        if(TextUtils.isEmpty(PREFERENCE_NAME)) {
            throw new Error("PerfrenceHelper must be  initialize in application before use ");
        } else if(context == null) {
            throw new Error("context is empty!!");
        }
    }

    public static void clearKey(Context context, String key) {
        SharedPreferences settings = getSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }
}
