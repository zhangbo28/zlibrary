package com.zhangbo.zlibrary.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by zhangbo on 2016/12/29.
 *
 */
public class MyDragLayout extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private View mMenuView,mMainView;
    private int mWidth;

    public MyDragLayout(Context context) {
        this(context,null);
    }

    public MyDragLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {

            //用户触摸到View后回调
            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                Log.d("zhangbo","onViewCaptured ");
            }

            //触摸时间的拖拽状态改变时回调
            @Override
            public void onViewDragStateChanged(int state) {
                Log.d("zhangbo","onViewDragStateChanged state=="+state);
            }

            //当View位置发生改变时回调
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                //Log.d("zhangbo","onViewPositionChanged "+changedView.getTag()+ left +"-"+ top);
            }

            //是否捕获当前触摸事件
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return mMainView==child;
            }

            //处理水平滑动
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            //处理垂直滑动
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top*7/8;
            }

            //拖动结束后调用
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if(mMainView.getLeft()<500){
                    mViewDragHelper.smoothSlideViewTo(mMainView,0,0);
                    ViewCompat.postInvalidateOnAnimation(MyDragLayout.this);
                }else{
                    mViewDragHelper.smoothSlideViewTo(mMainView,300,0);
                    ViewCompat.postInvalidateOnAnimation(MyDragLayout.this);
                }
            }

        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        mMenuView = getChildAt(0);
        mMainView = getChildAt(0);
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //mWidth = mMenuView.getMeasuredWidth();
//        if(mMainView!=null)
//        mMainView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("zhangbo","MyDragLayout onClick ");
//            }
//        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);

        return true;
    }

    @Override
    public void computeScroll() {
        if(mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
