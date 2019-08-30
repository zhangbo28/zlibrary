package com.zhangbo.zlibrary.widget;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

import com.zhangbo.zlibrary.R;


public class BasicPopup extends PopupWindow {
    public Context mContext;

    public BasicPopup(Context context) {
        this(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public BasicPopup(Context context, int width, int height) {
        super(context);
        mContext = context;
        setWidth(width);
        setHeight(height);
        setTouchable(true);
        setOutsideTouchable(true);
        setFocusable(true);
        ColorDrawable cd = new ColorDrawable(context.getResources().getColor(color.white));
        setBackgroundDrawable(cd);
    }


    /**
     * 让popwindow不受软键盘弹出影响，始终保持在固定位置
     */
    @SuppressLint("WrongConstant")
    public void setKeybroadHide(){
        this.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);//让popwindow不受软键盘弹出影响，始终保持在底部
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); //让popwindow不受软键盘弹出影响，始终保持在底部

    }


    public Context getContext() {
        return mContext;
    }

    public void showAtBottom(View view){
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        //华为7.0 当界面无焦点View时 isOpen仍然为true且activity.getCurrentFocus()返回为null
        if(isOpen)imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

        this.setAnimationStyle(R.style.pop_translate_top_buttom);

        showAtLocation(view, Gravity.BOTTOM,0,0);
    }

    public BasicPopup withShadow(final Activity activity){
        setShadeAlpha(activity.getWindow(),0.5f);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setShadeAlpha(activity.getWindow(),1.0f);
            }
        });

        return this;
    }

    /**
     * 设置外部阴影效果
     * @param window  activity.getWindow()
     * @param alpha   阴影透明度 0.0-1.0
     */
    private void setShadeAlpha(Window window, float alpha){
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句,解决部分手机ROM导致的阴影无效,官方文档解释是:让窗口背景后面的任何东西变暗
        window.setAttributes(layoutParams);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}