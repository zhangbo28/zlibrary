package com.zhangbo.zlibrary.mvpbase;


/**
 * Author: zhangbo
 * Data：2018/10/17
 * TODO: MVP框架核心基类，负责建立IView和IModel的连接
 *       IModel负责实现具体网络请求，IViewAdapter负责处理相应的数据
 *       IModel不可为空，IViewAdapter可为空(当请求的数据无须与View层关联时)
 *       此类BasePresent只处理基本的Present角色功能，一般请求事件通过{@link CommonPresent}实现
 */
public abstract class BasePresent<T> implements IPresent<T>,IHandler<T> {


    protected IModel mIModel;
    protected IViewAdapter mIView;

    public BasePresent() {

    }

    @Override
    public void setView(IViewAdapter<T> adapter) {
        this.mIView = adapter;
    }

    @Override
    public void dettchView() {
        this.mIView = null;
    }

    @Override
    public void setModel(IModel<T> model) {
        this.mIModel = model;
    }

    @Override
    public void build() {
        if(null==mIModel){
            throw new RuntimeException("Model is null, you need implement setModel(...)");
        }

    }

    @Override
    public void refresh() {
        onBefore();
        mIModel.refresh(this);
    }

    @Override
    public void onBefore() {

    }

    @Override
    public void onSuccess(T data) {
        if(mIView !=null) mIView.bindData(data);
    }

    @Override
    public void onFail(int code, String msg) {

    }



}
