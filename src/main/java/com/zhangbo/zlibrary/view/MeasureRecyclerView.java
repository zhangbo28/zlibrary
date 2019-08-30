package com.zhangbo.zlibrary.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by zhangbo on 2017/7/18.
 * @todo  嵌套 无滑动的RecyclerView
 */

public class MeasureRecyclerView extends RecyclerView {

        public MeasureRecyclerView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MeasureRecyclerView(Context context) {
            super(context);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        }


}
