package com.zhangbo.zlibrary.mvpbase;

import android.widget.AdapterView;

import com.zhangbo.zlibrary.widget.BaseRecyclerAdapter;

import java.util.Collection;

/**
 * Author: zhangbo
 * Data：2018/10/19
 * TODO: 纯列表请求时的View层接口的抽象实现类，同时也是RecyclerView.Adapter的子类
 *
 */
public abstract class IListAdapterImp<T> extends BaseRecyclerAdapter<T> implements IListAdapter<T> {

    public IListAdapterImp(int layoutId) {
        super(layoutId);
    }

    public IListAdapterImp(Collection<T> collection, int layoutId) {
        super(collection, layoutId);
    }

    public IListAdapterImp(Collection<T> collection, int layoutId, AdapterView.OnItemClickListener listener) {
        super(collection, layoutId, listener);
    }

    @Override
    public T getItem(int position) {
        return (T)super.getItem(position);
    }


    @Override
    public void bindData(Object data) {

    }
}
