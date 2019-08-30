package com.zhangbo.zlibrary.mvpbase;

 /**
 * Author: zhangbo
 * Data：2018/10/17
 * TODO: View层接口
 */

public interface IViewAdapter<T> {

    void bindData(T data);
}