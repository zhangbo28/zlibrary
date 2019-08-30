package com.zhangbo.zlibrary.mvpbase;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhangbo.zlibrary.util.ListUtils;
import com.zhangbo.zlibrary.util.SizeUtils;
import com.zhangbo.zlibrary.view.LoadingLayout;
import com.zhangbo.zlibrary.widget.RecyclerListDivider;

import java.util.List;

/**
 * Author: zhangbo
 * Data：2018/10/18
 * TODO: 纯列表请求的Present角色，封装了上下拉加载和视图转换的逻辑
 *       使用中须绑定IListModel和IListAdapter的实例
 */
public class ListPresent extends BasePresent {


    protected IListAdapter mListAdapter;
    protected IListModel mListModel;

    Context mContext;
    View mContentView;  /** 对于View的交互逻辑都应该独立于当前Present类进行管理 并进行接口隔离 支持自定义并提供默认实现 待完善 **/
    LoadingLayout mLoadingLayout;
    RecyclerView mRecyclerView;
    RefreshLayout mRefreshLayout;

    boolean hasContent;
    boolean isLoadMore;

    public ListPresent(View contentView) {
        this.mContentView = contentView;
        mContext = mContentView.getContext();

    }

    private void initView(){
        if(mContentView!=null){
            traverseView(mContentView);

            if(mLoadingLayout==null)mLoadingLayout = LoadingLayout.wrap(mContentView);

            /**此处对于View的配置应该独立于当前类进行管理 待调整**/
            if(mRecyclerView!=null){
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                mRecyclerView.addItemDecoration(new RecyclerListDivider(SizeUtils.dp2px(mContext,10),0xffF7F4F8));
                mRecyclerView.setAdapter((RecyclerView.Adapter)mListAdapter);
            }

            if(mRefreshLayout!=null){
                if(mRefreshLayout.getRefreshHeader()==null)mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
                if(mRefreshLayout.getRefreshFooter()==null)mRefreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
                mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                    @Override
                    public void onLoadMore(RefreshLayout refreshLayout) {
                        loadMore();
                    }

                    @Override
                    public void onRefresh(RefreshLayout refreshLayout) {
                        refresh();
                    }
                });
            }
        }

    }

    @Override
    public void build() {
        if(null==mIModel){
            throw new RuntimeException("Model is null, you need implement setModel(...)");
        }

        if (null==mIView) {
            throw new RuntimeException("Adapter is null, you need implement setView(...)");
        }

        if(mIModel instanceof IListModel){
            mListModel = (IListModel) mIModel;
        }else{
            throw new RuntimeException("You should setModel() with IListModel instead of IModel");
        }
        if(mIView instanceof IListAdapter){
            mListAdapter = (IListAdapter) mIView;
        }else{
            throw new RuntimeException("You should setView() with IListAdapter instead of IViewAdapter");
        }
        initView();

    }

    @Override
    public void refresh() {
        isLoadMore = false;
        super.refresh();
    }

    private void loadMore(){
        isLoadMore = true;
        mListModel.loadMore(this);
    }


    @Override
    public void onBefore() {
        if(mLoadingLayout!=null && !hasContent){
            mLoadingLayout.showLoading();
        }

    }

    /**同理对于LoadingLayout和Refresh的交互也应该独立于当前类进行管理 待调整**/
    @Override
    public void onSuccess(Object data) {
        super.onSuccess(data);
        if(mRefreshLayout!=null){
            refreshLayoutFinish();

        }
        if(data==null){
            return;
        }

        if(data instanceof IListResult){
            setListData((IListResult)data);
        }

    }

    private void setListData(IListResult listResult){
        List list = listResult.getList();

        if(ListUtils.isEmpty(list) && !isLoadMore){
            if(mLoadingLayout!=null){
                mLoadingLayout.showEmpty();
            }
        }else{
            hasContent = true;
            if(mLoadingLayout!=null){
                mLoadingLayout.showContent();
            }
        }

        if(isLoadMore){
            mListAdapter.loadMore(list);
        }else{
            mListAdapter.refresh(list);
        }


    }



    @Override
    public void onFail(int code, String msg) {
        if(mLoadingLayout!=null && !isLoadMore){
            hasContent = false;
            mLoadingLayout.setErrorText(msg);
            mLoadingLayout.showError();
            mLoadingLayout.setRetryListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refresh();
                }
            });
        }
        if(mRefreshLayout!=null){
            refreshLayoutFinish();
        }

    }

    @Override
    public void dettchView() {
        super.dettchView();
        mContext = null;
        mRecyclerView = null;
        mRefreshLayout = null;
        mContentView = null;
        mLoadingLayout = null;
    }

    private void refreshLayoutFinish(){
        if(!isLoadMore){
            if(mRecyclerView!=null)mRecyclerView.scrollToPosition(0);
            mRefreshLayout.finishRefresh();
            mRefreshLayout.setNoMoreData(!mListModel.hasMoreData());
        }else{
            mRefreshLayout.finishLoadMore(1000,true,!mListModel.hasMoreData());
        }
    }


    protected void traverseView(View view) {

        if (mLoadingLayout==null && view instanceof LoadingLayout) {
            mLoadingLayout = (LoadingLayout) view;
        }

        if (mRecyclerView==null && view instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) view;
        }

        if (mRefreshLayout==null && view instanceof RefreshLayout) {
            mRefreshLayout = (RefreshLayout) view;
        }

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0, count = viewGroup.getChildCount(); i < count; i++) {
                if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                    traverseView(viewGroup.getChildAt(i));
                }
            }
        }
    }

}
