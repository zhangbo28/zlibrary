package com.zhangbo.zlibrary.mvpbase;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhangbo.zlibrary.util.Toaster;
import com.zhangbo.zlibrary.widget.LoadingDialog;
import com.zhangbo.zlibrary.view.LoadingLayout;

/**
 * Author: zhangbo
 * Data：2018/10/18
 * TODO: 通用的IPresent角色实现，可处理任何与View有关的数据请求
 *       且支持2种View层与请求流程的交互逻辑。
 *       与View无关联的请求时最好使用第二种，即由LoadingDialog处理
 * //    废弃  由 {@link LoadPresent} 和 {@link SubmitPresent}根据场景替换
 */
public class CommonPresent<T> extends BasePresent<T> {

    Context mContext;
    View mContentView;  //contentView为ScrollView时LoadingLayout有bug，待完善
    LoadingLayout mLoadingLayout;
    LoadingDialog mLoadingDialog;
    RefreshLayout mRefreshLayout;

    /**
     *  通过此构造函数初始化时，视图交互逻辑交由LoadingLayout处理
     *  contentView的视图结构中有无LoadingLayout对象不影响。
     *  contentView建议传Activity或Fragment中的纯内容视图(不含TitleLayout的父布局)，不建议可传XML中的根视图，具体参考{@link LoadingLayout}
     * @param contentView
     */
    public CommonPresent(View contentView) {
        this(contentView,null);

    }

    /**
     * 通过此构造函数初始化时，视图交互逻辑交由LoadingDialog处理
     * 也就是dialog对象的show()和dismiss()，即与原有的交互保持一致，建议后续迭代统一使用第一种方式
     * @param activity
     */
    public CommonPresent(Activity activity) {
        this(null,activity);

    }


    public CommonPresent(View contentView, Activity activity) {
        this.mContentView = contentView;
        initViews(activity);

    }

    @Override
    public void build() {
        super.build();
    }

    private void initViews(Activity activity){
        if(mContentView!=null){
            traverseView(mContentView);
            if(mLoadingLayout==null)
                mLoadingLayout = LoadingLayout.wrap(mContentView);

            mContext = mContentView.getContext().getApplicationContext();

        }else if(activity!=null){
            mLoadingDialog = new LoadingDialog(activity);
            mContext = activity.getApplicationContext();
        }

        if(mRefreshLayout!=null){
            mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    mIModel.refresh(CommonPresent.this);
                }
            });
        }
    }


    @Override
    public void onBefore() {
        if(mLoadingLayout!=null){
            mLoadingLayout.showLoading();
        }else if(mLoadingDialog!=null){
            mLoadingDialog.show();
        }

    }

    @Override
    public void onSuccess(T data) {
        if(mLoadingLayout!=null){
            mLoadingLayout.showContent();
        }else if(mLoadingDialog!=null){
            mLoadingDialog.dismiss();
        }

        if(mRefreshLayout!=null && mRefreshLayout.isRefreshing())mRefreshLayout.finishRefresh();

        if(mIView !=null) mIView.bindData(data);
    }

    @Override
    public void onFail(int code, String msg) {
        if(mLoadingLayout!=null){
            mLoadingLayout.setErrorText(msg);
            mLoadingLayout.showError();
            mLoadingLayout.setRetryListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refresh();
                }
            });
        }else if(mLoadingDialog!=null && mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
            Toaster.show(mContext,msg);
        }
        if(mRefreshLayout!=null && mRefreshLayout.isRefreshing())mRefreshLayout.finishRefresh(false);

    }

    @Override
    public void dettchView() {
        super.dettchView();
        if(mLoadingDialog!=null)mLoadingDialog.dismiss();
        mContentView = null;
        mLoadingLayout = null;
        mLoadingDialog = null;
        mRefreshLayout = null;
        mContext = null;
    }


    protected void traverseView(View view) {

        if (mLoadingLayout == null && view instanceof LoadingLayout) {
            mLoadingLayout = (LoadingLayout) view;
        }
        if (mRefreshLayout==null && view instanceof RefreshLayout) {
            mRefreshLayout = (RefreshLayout) view;
        }

        if (mLoadingLayout == null && view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0, count = viewGroup.getChildCount(); i < count; i++) {
                if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                    traverseView(viewGroup.getChildAt(i));
                }
            }
        }
    }

}
