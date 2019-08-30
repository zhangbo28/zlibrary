package com.zhangbo.zlibrary.mvpbase;

/**
 * Author: zhangbo
 * Data：2018/10/17
 * TODO: Present层接口
 */
public interface IPresent<T>{


    void setModel(IModel<T> model);

    void setView(IViewAdapter<T> adapter);

    void build();

    void refresh();

    void dettchView();

}
