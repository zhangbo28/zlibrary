package com.zhangbo.zlibrary.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Author: zhangbo
 * Data：2018/8/14
 * TODO:
 */
public class Toaster {

    private static Toast mToast;

    public static void show(Context context,String msg){
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            //mToast.setGravity(Gravity.BOTTOM, 0, 0);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

}
