package com.zhangbo.zlibrary.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author: zhangbo
 * Data：2018/12/07
 * TODO: CoordinatorLayout布局底部菜单上滑隐藏/下滑展示的Behavior
 */
public class BottomPullHideBehavior extends CoordinatorLayout.Behavior {

    private ObjectAnimator outAnimator, inAnimator;

    public BottomPullHideBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return super.onDependentViewChanged(parent,child,dependency);
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        //LogUtils.d("onStartNestedScroll--directTargetChild=="+directTargetChild+"target=="+target);
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }


    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
//        super.onStopNestedScroll(coordinatorLayout, child, target);
//        int scrollY = target.getScrollY();
//        int top = target.getTop();
//        LogUtils.d("onStopNestedScroll--scrollY=="+scrollY +"top=="+top);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        if (dy > 0) {// 上滑隐藏
            if (outAnimator == null) {
                outAnimator = ObjectAnimator.ofFloat(child, "translationY", 0, child.getHeight());
                outAnimator.setDuration(300);
            }
            if (!outAnimator.isRunning() && child.getTranslationY() <= 0) {
                outAnimator.start();
            }
        } else if (dy < 0) {// 下滑显示
            if (inAnimator == null) {
                inAnimator = ObjectAnimator.ofFloat(child, "translationY", child.getHeight(), 0);
                inAnimator.setDuration(300);
            }
            if (!inAnimator.isRunning() && child.getTranslationY() >= child.getHeight()) {
                inAnimator.start();
            }
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }
}
