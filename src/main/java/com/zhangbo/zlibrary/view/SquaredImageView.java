package com.zhangbo.zlibrary.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Author: zhangbo
 * Data：2018/8/27
 * TODO: 正方形ImageView
 */
public class SquaredImageView extends android.support.v7.widget.AppCompatImageView{

    int mSize = -1;


    public SquaredImageView(Context context) {
        super(context);
    }

    public SquaredImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquaredImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = mSize>0? mSize : getMeasuredWidth();
        // 高度和宽度一样
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public void setSize(int size) {
        this.mSize = size;
    }
}
