package com.zhangbo.zlibrary.mvpbase;

/**
 * Author: zhangbo
 * Data：2018/10/19
 * TODO: 纯列表请求Model接口
 */
public interface IListModel<T> extends IModel<IListResult<T>> {

    void loadMore(IHandler<IListResult<T>> handler);

    boolean hasMoreData();

}
