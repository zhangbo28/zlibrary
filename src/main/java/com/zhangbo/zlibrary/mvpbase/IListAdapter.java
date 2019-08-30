package com.zhangbo.zlibrary.mvpbase;

import java.util.Collection;

/**
 * Author: zhangbo
 * Data：2018/10/19
 * TODO: 纯列表请求时的View层接口，参考抽象实现类{@link IListAdapterImp}
 */
public interface IListAdapter<T> extends IViewAdapter{

    void refresh(Collection<T> list);

    void loadMore(Collection<T> list);

    T getItem(int position);

}
