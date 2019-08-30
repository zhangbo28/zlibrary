package com.zhangbo.zlibrary.mvpbase;

import com.zhangbo.zlibrary.mvpbase.IHandler;
import com.zhangbo.zlibrary.mvpbase.IListModel;

/**
 * Author: zhangbo
 * Data：2018/10/19
 * TODO: 列表类请求的抽象实现，具体实现中只需要处理加载请求即可，无须处理上下拉请求
 */
public abstract class IListModelImp<T> implements IListModel<T> {

    protected int pageIndex = 1;
    protected int pageSize = 10;
    protected int totalCount;

    public IListModelImp() {
        this(10);
    }

    public IListModelImp(int pageSize) {
        this.pageSize = pageSize;
    }

    public abstract void request(IHandler<IListResult<T>> handler);

    @Override
    public void loadMore(IHandler<IListResult<T>> handler) {
        pageIndex++;
        request(handler);
    }

    @Override
    public boolean hasMoreData() {
        int i = totalCount%pageSize;
        int pageCount = i>0? totalCount/pageSize+1 :totalCount/pageSize;
        return pageCount > pageIndex;
    }

    @Override
    public void refresh(IHandler<IListResult<T>> handler) {
        pageIndex = 1;
        request(handler);
    }

}
