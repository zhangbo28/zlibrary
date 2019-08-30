package com.zhangbo.zlibrary.rxwraper;

import com.zhangbo.zlibrary.mvpbase.ILoadingView;

import io.reactivex.disposables.Disposable;

/**
 * Author: zhangbo
 * Data：2019/1/3
 * TODO:
 */
public class ViewObserver<T> extends BaseObserver<T>{

    ILoadingView mSwitcher;

    public ViewObserver() {
    }

    public ViewObserver(ILoadingView mSwitcher) {
        this.mSwitcher = mSwitcher;
    }

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        mSwitcher.showLoading();
    }

    @Override
    public void onSuccess(T t) {

        mSwitcher.showContent();
    }

    @Override
    public void onFail(int code, String message) {
        super.onFail(code, message);
    }
}
