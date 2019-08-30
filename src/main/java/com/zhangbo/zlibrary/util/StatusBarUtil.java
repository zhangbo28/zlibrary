package com.zhangbo.zlibrary.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Author: zhangbo
 * Data：2018/9/7
 * TODO:
 */
public class StatusBarUtil  {

    /**
     * 将acitivity中的状态栏设置为一个纯色
     * @param activity 需要设置的activity
     * @param color 设置的颜色（一般是titlebar的颜色）
     */
    public static void setColor(Activity activity, int color){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置statusBar的背景色
            activity.getWindow().setStatusBarColor(color);

            /** 6.0以上需要用如下代码处理兼容，不然设置的状态栏颜色会与内容视图有明显的分界线 **/
            /*View v = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
            if (v != null) {
                v.setForeground((Drawable)null);
            }*/
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //此处设置状态栏透明后content视图就用铺满全屏占用状态栏的空间
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //然后创建一个和状态栏高度一样的View添加到根视图(FrameLayout)中,但会覆盖在contentView上
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(color);
            decorView.addView(statusBarView, lp);

            //此处等同于通过代码设置xml布局文件中的根视图android:fitsSystemWindows="true"
            //效果就是让xml布局的视图也就是contentView不占用状态栏空间，也就是上面创建的statusBarView铺在了原状态栏的位置，间接实现了改变状态栏颜色的效果
            //注意的是setStatusBarColor函数需要在Activity的setContentView之后调用，否则下面的代码设置fitsSystemWindows属性无效(childView为空)
            ViewGroup contentView = (ViewGroup)activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
            View childView = contentView.getChildAt(0);//此处的childView就是当前activity的xml布局文件中的根视图
            if (childView != null) {
                childView.setFitsSystemWindows(true);
                //setPadding(activity,childView);///其实通过手动设置布局文件根视图的PaddingTop增高和状态栏等高的高度，效果是一样的(我真是个天才~~)
            }


        }
    }


    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /*********************************************沉浸式****************************************************/

    private static float DEFAULT_ALPHA = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? 0.2f : 0.3f;

    public static void immersiveStatusBar(Activity activity) {
        immersiveStatusBar(activity, 0);
    }


    /**
     * Android4.4以上的沉浸式全屏模式
     * @param window 一般都是用于Activity的window,也可以是其他的例如Dialog,DialogFragment
     */
    public static void immersiveStatusBar(Window window) {
        immersiveStatusBar(window, 0);
    }
    /**
     * @param alpha    透明栏透明度[0.0-1.0]
     */
    public static void immersiveStatusBar(Activity activity, @FloatRange(from = 0.0, to = 1.0) float alpha) {
        immersiveStatusBar(activity.getWindow(), alpha);
    }

    /**
     * Android4.4以上的沉浸式全屏模式
     * 注:
     * 1.删除fitsSystemWindows属性:Android5.0以上使用该方法如果出现界面展示不正确,删除布局中所有fitsSystemWindows属性
     * 或者调用forceFitsSystemWindows方法
     * 2.不删除fitsSystemWindows属性:也可以区别处理,Android5.0以上使用自己的方式实现,不调用该方法
     *
     * @param window 一般都是用于Activity的window,也可以是其他的例如Dialog,DialogFragment
     * @param alpha  透明栏透明度[0.0-1.0]
     */
    public static void immersiveStatusBar(Window window, @FloatRange(from = 0.0, to = 1.0) float alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

            //完全的沉浸式模式
//            int immersiveMode = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(systemUiVisibility);

        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }


    /** 增加View的高度以及paddingTop,增加的值为状态栏高度.一般是在沉浸式全屏给ToolBar用的 */
    public static void setHeightAndPadding(Context context, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            lp.height += getStatusBarHeight(context);//增高
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(context),
                    view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    /** 增加View的paddingTop,增加的值为状态栏高度 */
    public static void setPadding(Context context, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(context),
                    view.getPaddingRight(), view.getPaddingBottom());
        }
    }


    /**
     * 沉浸式模式的三种解决方案，参考文章：https://blog.csdn.net/guolin_blog/article/details/51763825
     * @param activity
     */
    public static void immersive(Activity activity){
        /**① 纯粹设置decorView全屏显示，状态栏会被完全隐藏，支持最低Android4.1**/
//        View decorView = activity.getWindow().getDecorView();
//        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(option);
          //官方建议ActionBar不应独立于状态栏显示，这里隐藏，如果使用的是NoActionBar的主题可以不管
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();


        /**② 两个Flag必须要结合在一起使用，表示会让应用的主体内容占用系统状态栏的空间，并设置状态栏背景色为透明
         * 呈现的效果就是内容布局全屏显示，状态栏内容覆盖在屏幕内容上面，但背景色为透明**/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//这个flag不加 三星手机设置状态栏颜色无效
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT); //setStatusBarColor为5.0新增
//
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);

        }
//        ActionBar actionBar = getSupportActionBar();//同上
//        actionBar.hide();


        /**③真正的完全沉浸式模式，需要在Activity的onWindowFocusChanged函数中复写，效果为默认全屏显示内容，当点击屏幕后
         * 状态栏和导航栏才会显示出来，一段时间屏幕无感应后继续隐藏，一般在视频和游戏类项目中才会需要这种沉浸式体验，方案②中的效果就能满足其他的所谓的“沉浸式”需求 **/
//        @Override
//        public void onWindowFocusChanged(boolean hasFocus) {
//            super.onWindowFocusChanged(hasFocus);
//            if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//                View decorView = getWindow().getDecorView();
//                decorView.setSystemUiVisibility(
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//            }
//        }
    }


}