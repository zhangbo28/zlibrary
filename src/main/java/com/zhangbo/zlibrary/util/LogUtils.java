package com.zhangbo.zlibrary.util;

import android.util.Log;

/**
 * Author: zhangbo
 * Data：2018/9/6
 * TODO:
 */
public class LogUtils {

    public static boolean logEnable = true;

    public static final String TAG = "zhangbo";

    public static void d(String msg){
        if(logEnable){
            Log.d(TAG,msg);
        }
    }

    public static void i(String msg){
        if(logEnable){
            Log.i(TAG,msg);
        }
    }

    public static void e(String msg){
        if(logEnable){
            Log.e(TAG,msg);
        }
    }

    public static void w(String msg){
        if(logEnable){
            Log.w(TAG,msg);
        }
    }

    public static void v(String msg){
        if(logEnable){
            Log.v(TAG,msg);
        }
    }
}
