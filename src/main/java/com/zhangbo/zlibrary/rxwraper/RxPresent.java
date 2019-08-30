package com.zhangbo.zlibrary.rxwraper;

import com.zhangbo.zlibrary.mvpbase.ILoadingView;
import com.zhangbo.zlibrary.mvpbase.IViewAdapter;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Author: zhangbo
 * Data：2019/1/2
 * TODO:
 */
public class RxPresent implements IRxPresent{

    Observable mObservable;

    IViewAdapter mAdapter;

    ILoadingView mViewSwitcher;

    Disposable mDisposable;

    @Override
    public IRxPresent bindObservable(Observable observable) {
        this.mObservable = observable;
        return this;
    }

    @Override
    public IRxPresent bindObserver(IViewAdapter adapter) {
        this.mAdapter = adapter;
        return this;
    }

    @Override
    public void execute() {
        mObservable.compose(RxSchedulers.io_main())
                .subscribe(new BaseObserver() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                        if(mViewSwitcher!=null)
                            mViewSwitcher.showLoading();
                    }

                    @Override
                    public void onSuccess(Object o) {
                        if(mAdapter!=null)
                            mAdapter.bindData(o);

                        if(mViewSwitcher!=null)
                            mViewSwitcher.showContent();
                    }

                    @Override
                    public void onFail(int code, String message) {
                        if(mViewSwitcher!=null)
                            mViewSwitcher.showError();
                    }

                });
    }

    @Override
    public void detach() {
        if(mDisposable!=null && !mDisposable.isDisposed())
            mDisposable.dispose();

    }
}
