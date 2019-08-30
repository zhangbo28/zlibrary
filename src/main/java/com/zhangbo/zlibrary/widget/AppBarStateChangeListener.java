package com.zhangbo.zlibrary.widget;

import android.support.design.widget.AppBarLayout;

/**
 * Created by zhangbo on 2017/10/11.
 * AppBarLayout状态监听器
 */
public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {
 
    public enum State {
        EXPANDED,  //展开状态
        COLLAPSED, //折叠状态
        IDLE       //中间状态
    }
 
    private State mCurrentState = State.IDLE;
 
    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE);
            }
            mCurrentState = State.IDLE;
        }

        onOffsetImp(appBarLayout,i);
    }
 
    public abstract void onStateChanged(AppBarLayout appBarLayout, State state);

    public void onOffsetImp(AppBarLayout appBarLayout, int i){
        //如果需要实时的间距，复写此函数
    }
}