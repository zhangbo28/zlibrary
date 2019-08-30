package com.zhangbo.zlibrary.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author: zhangbo
 * Data：2018/12/07
 * TODO: CoordinatorLayout顶部上滑展示/下滑隐藏的Behavior
 */
public class TopPullToShowBehavior extends CoordinatorLayout.Behavior {
    float distance = 0;

    public TopPullToShowBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (distance == 0) {
            distance = dependency.getY() - child.getHeight();
        }

        float dy = dependency.getY() - child.getHeight();
        dy = dy < 0 ? 0 : dy;

        float alpha = 1 - (dy / distance);
        child.setAlpha(alpha);

        float y = -(dy / distance) * child.getHeight();
        child.setTranslationY(y);
        return true;
    }

//    @Override
//    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
//        LogUtils.d("onStartNestedScroll--directTargetChild=="+directTargetChild+"target=="+target);
//        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
//    }


    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
//        super.onStopNestedScroll(coordinatorLayout, child, target);
//        int scrollY = target.getScrollY();
//        int top = target.getTop();
//        LogUtils.d("onStopNestedScroll--scrollY=="+scrollY +"top=="+top);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
//        int scrollY = target.getScrollY();
//        int top = target.getTop();
//        LogUtils.d("onNestedScroll--scrollY=="+scrollY +"top=="+top);
    }

}
