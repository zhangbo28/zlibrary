package com.zhangbo.zlibrary.mvpbase;

/**
 * Author: zhangbo
 * Data：2018/10/17
 * TODO: Model层接口
 */
public interface IModel<T>{

    void refresh(IHandler<T> handler);
}
