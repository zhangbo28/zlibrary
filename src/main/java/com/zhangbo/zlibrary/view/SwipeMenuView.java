package com.zhangbo.zlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;

import com.zhangbo.zlibrary.util.LogUtils;

import java.util.List;

/**
 * Author: zhangbo
 * Data：2018/12/4
 * TODO: 支持侧滑菜单的自定义View
 */
public class SwipeMenuView extends ViewGroup {

    int mLastX,mLastY;
    private Scroller mScroller;

    private View mContentView;
    private List<View> mMenuViews;


    public SwipeMenuView(Context context) {
        this(context,null);
    }

    public SwipeMenuView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

//
//    public SwipeMenuView wrap(View contentView){
//        mContentView = contentView;
//        addView(mContentView,0);
//
//        return this;
//    }

    public SwipeMenuView menu(String text){
        TextView menuText = new TextView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        menuText.setText(text);
        menuText.setGravity(Gravity.CENTER);
        addView(menuText,params);

        return this;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        mContentView = getChildAt(0);

        measureChild(mContentView,widthMeasureSpec,heightMeasureSpec);
        int width = mContentView.getMeasuredWidth();
        int height = mContentView.getMeasuredHeight();

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        Log.d("zhangbo","onLayout-- l=="+l+" t=="+t+" r=="+r+" b=="+b);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        for(int i = 0;i<childCount;i++){
            View child = getChildAt(i);
            int w = child.getMeasuredWidth();
            int h = child.getMeasuredHeight();
            if(i==0){
                child.layout(l,t,r,b);
            }else{
                child.layout(r+(i-1)*height,t,r+height*i,b);
            }
        }


    }

    /**
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;

                if(Math.abs(offsetX)>Math.abs(offsetY)){ //适用于横向滑动
                    scrollBy(-offsetX,-offsetY);
                }

                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mScroller.startScroll(getScrollX(),getScrollY(),-getScrollX(),-getScrollY());
                invalidate();  //手动调用此方法来重绘页面 进而调用computeScroll方法


                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 页面重绘时会调用此方法
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
}
