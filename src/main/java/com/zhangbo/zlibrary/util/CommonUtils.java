package com.zhangbo.zlibrary.util;

/**
 * Author: zhangbo
 * Data：2018/9/13
 * TODO:
 */
public class CommonUtils {

    private static long lastClickTime;

    /**
     * 防止用户快速点击2级造成错误(默认间隔800毫秒)
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(800);
    }

    /**
     * 防止用户快速点击2级造成错误
     *
     * @param timeMillis 快速点击的间隔时间
     * @return
     */
    public static boolean isFastDoubleClick(int timeMillis) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < timeMillis) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
