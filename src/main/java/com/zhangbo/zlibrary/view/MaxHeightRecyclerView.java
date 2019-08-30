package com.zhangbo.zlibrary.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Author: zhangbo
 * Data：2018/10/23
 * TODO:
 */
public class MaxHeightRecyclerView extends RecyclerView{

    int maxHeight;

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public MaxHeightRecyclerView(Context context) {
        super(context);
    }

    public MaxHeightRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MaxHeightRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (maxHeight > 0) {
            heightSpec = MeasureSpec.makeMeasureSpec(maxHeight,
                    MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthSpec, heightSpec);
    }
}
