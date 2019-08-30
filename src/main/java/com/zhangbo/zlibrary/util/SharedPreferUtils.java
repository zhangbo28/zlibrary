package com.zhangbo.zlibrary.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

//import com.sijibao.coaltrade.base.MyApp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Author: zhangbo
 * Data：2018/8/7
 * TODO: 轻量级数据本地缓存工具类
 */
public class SharedPreferUtils {
    private static final String PREF_NAME_DEFAULT = "LocalCache";

//    private SharedPreferences sharedPreferences;
//
//    private SharedPreferences.Editor editor;
//
//    public SharedPreferUtils() {
//        sharedPreferences = MyApp.getContext().getSharedPreferences(PREF_NAME_DEFAULT,
//                Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//    }

    /**
     * 存储
     */
//    public static void put(String key, Object object) {
//        SharedPreferences preferences = MyApp.getContext().getSharedPreferences(PREF_NAME_DEFAULT, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        if (object instanceof String) {
//            editor.putString(key, (String) object);
//        } else if (object instanceof Integer) {
//            editor.putInt(key, (Integer) object);
//        } else if (object instanceof Boolean) {
//            editor.putBoolean(key, (Boolean) object);
//        } else if (object instanceof Float) {
//            editor.putFloat(key, (Float) object);
//        } else if (object instanceof Long) {
//            editor.putLong(key, (Long) object);
//        } else {
//            editor.putString(key, object.toString());
//        }
//        editor.commit();
//    }

    /**
     * 获取保存的数据
     */
//    public static Object get(String key, Object defaultObject) {
//        SharedPreferences preferences = MyApp.getContext().getSharedPreferences(PREF_NAME_DEFAULT, Context.MODE_PRIVATE);
//        if (defaultObject instanceof String) {
//            return preferences.getString(key, (String) defaultObject);
//        } else if (defaultObject instanceof Integer) {
//            return preferences.getInt(key, (Integer) defaultObject);
//        } else if (defaultObject instanceof Boolean) {
//            return preferences.getBoolean(key, (Boolean) defaultObject);
//        } else if (defaultObject instanceof Float) {
//            return preferences.getFloat(key, (Float) defaultObject);
//        } else if (defaultObject instanceof Long) {
//            return preferences.getLong(key, (Long) defaultObject);
//        } else {
//            return preferences.getString(key, null);
//        }
//    }



    /**
     * 保存对象到本地SP中
     * @param context
     * @param t
     * @param preferenceId
     * @param <T>
     */
    public static <T>void saveObj2SP(Context context, T t, String preferenceId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME_DEFAULT, Context.MODE_PRIVATE);
        ByteArrayOutputStream bos;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(t);
            byte[] bytes = bos.toByteArray();
            String ObjStr = Base64.encodeToString(bytes, Base64.DEFAULT);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(preferenceId, ObjStr);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    /**
     * 从本地SP中读取对象
     * @param context
     * @param preferenceId
     * @param <T>
     * @return
     */
    public static <T extends Object> T getObjFromSP(Context context, String preferenceId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME_DEFAULT, Context.MODE_PRIVATE);
        byte[] bytes = Base64.decode(preferences.getString(preferenceId, ""), Base64.DEFAULT);
        ByteArrayInputStream bis;
        ObjectInputStream ois = null;
        T obj = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = (T) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    /**
     * 清楚暂存数据
     * @param key
     */
//    public static void remove(String key){
//        SharedPreferences preferences = MyApp.getContext().getSharedPreferences(PREF_NAME_DEFAULT, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.remove(key).commit();
//    }
}
