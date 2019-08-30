package com.zhangbo.zlibrary.mvpbase;

/**
 * Author: zhangbo
 * Data：2019/3/6
 * TODO:
 */
public abstract class SubmitCallBack<T> implements IViewAdapter<T> {
    @Override
    public void bindData(T data) {
        success(data);
    }

    public abstract void success(T t);


    public abstract void fail(int code,String msg);

}
