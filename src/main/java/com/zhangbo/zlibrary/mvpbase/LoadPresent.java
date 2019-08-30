package com.zhangbo.zlibrary.mvpbase;


import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhangbo.zlibrary.view.LoadingLayout;

/**
 * Author: zhangbo
 * Data：2019/3/4
 * TODO: 用户加载数据的Present角色，利用ILoadingView处理网络请求中的UI交互
 */
public class LoadPresent<T> extends BasePresent<T>{

    View mContentView;
    ILoadingView mILoadView;
    RefreshLayout mRefreshLayout;

    public LoadPresent(View contentView) {
        mContentView = contentView;
        initViews();
    }

    @Override
    public void onBefore() {
        if(mILoadView!=null){
            mILoadView.showLoading();
        }

    }

    @Override
    public void onSuccess(T data) {
        if(mILoadView!=null){
            mILoadView.showContent();
        }
        if(mRefreshLayout!=null && mRefreshLayout.isRefreshing())mRefreshLayout.finishRefresh();

        if(mIView !=null) mIView.bindData(data);
    }

    @Override
    public void onFail(int code, String msg) {
        if(mILoadView!=null){
            mILoadView.showError();
            if(mILoadView instanceof LoadingLayout){
                LoadingLayout loadingLayout = (LoadingLayout) mILoadView;
                loadingLayout.setErrorText(msg);
                loadingLayout.setRetryListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refresh();
                    }
                });
            }
        }
        if(mRefreshLayout!=null && mRefreshLayout.isRefreshing())mRefreshLayout.finishRefresh(false);

    }

    public void setRefresh(RefreshLayout refreshLayout){
        mRefreshLayout = refreshLayout;

    }

    @Override
    public void dettchView() {
        super.dettchView();
        mContentView = null;
        mILoadView = null;
        mRefreshLayout = null;
    }


    private void initViews(){
        if(mContentView!=null){
            traverseView(mContentView);
            if(mILoadView==null)
                mILoadView = LoadingLayout.wrap(mContentView);

        }
        if(mRefreshLayout!=null){
            mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    mIModel.refresh(LoadPresent.this);
                }
            });
        }


    }

    protected void traverseView(View view) {

        if (mILoadView == null && view instanceof ILoadingView) {
            mILoadView = (ILoadingView) view;
        }
        if (mRefreshLayout==null && view instanceof RefreshLayout) {
            mRefreshLayout = (RefreshLayout) view;
        }

        if (mILoadView == null && view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0, count = viewGroup.getChildCount(); i < count; i++) {
                if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                    traverseView(viewGroup.getChildAt(i));
                }
            }
        }
    }
}
