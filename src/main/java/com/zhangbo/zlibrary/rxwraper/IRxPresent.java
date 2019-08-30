package com.zhangbo.zlibrary.rxwraper;

import com.zhangbo.zlibrary.mvpbase.IViewAdapter;

import io.reactivex.Observable;

/**
 * Author: zhangbo
 * Data：2019/1/4
 * TODO:
 */
public interface IRxPresent {

    IRxPresent bindObservable(Observable observable);

    IRxPresent bindObserver(IViewAdapter adapter);

    void execute();

    void detach();

}
