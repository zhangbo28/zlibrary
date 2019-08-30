package com.zhangbo.zlibrary.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhangbo on 2017/7/10.
 * @todo 自定义RecyclerView的分割线(网格)
 */

public class RecyclerGridDivider extends RecyclerView.ItemDecoration{
    private Paint mPaint;
    private int mDividerHeight = 2;


    /** Default **/
    public RecyclerGridDivider() {
        this(1,0xffe5e5e5);
    }

    public RecyclerGridDivider(int dividerHeight, int dividerColor) {
        mDividerHeight = dividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mDividerHeight, mDividerHeight, mDividerHeight, mDividerHeight);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

//        final int left = parent.getPaddingLeft();
//        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();

        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            //drawable top
            int bottom1 = child.getTop() + layoutParams.topMargin;
            int top1 = bottom1 - mDividerHeight;
            c.drawRect(child.getLeft(), top1, child.getRight(), bottom1, mPaint);

            //drawable left
            int right2 = child.getLeft() - layoutParams.leftMargin;
            int left2 = right2 - mDividerHeight;
            c.drawRect(left2, child.getTop(), right2, child.getBottom(), mPaint);

            //drawable bottom
            int top3 = child.getBottom() + layoutParams.bottomMargin;
            int bottom3 = top3 + mDividerHeight;
            c.drawRect(child.getLeft(), top3, child.getRight(), bottom3, mPaint);

            //drawable right
            int left4 = child.getRight() + layoutParams.leftMargin;
            int right4 = left4 + mDividerHeight;
            c.drawRect(left4, child.getTop(), right4, child.getBottom(), mPaint);
        }
    }

}
