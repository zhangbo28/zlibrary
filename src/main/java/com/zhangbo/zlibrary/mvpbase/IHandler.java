package com.zhangbo.zlibrary.mvpbase;

/**
 * Author: zhangbo
 * Data：2018/10/17
 * TODO:通用接口，用于将IModel角色网络请求中的流程同步到IPresent角色
 */
public interface IHandler<T>{

    void onBefore();

    void onSuccess(T data);

    void onFail(int code, String msg);
}
