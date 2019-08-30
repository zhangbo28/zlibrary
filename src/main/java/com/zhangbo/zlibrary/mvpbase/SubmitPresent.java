package com.zhangbo.zlibrary.mvpbase;

import android.content.Context;
import com.zhangbo.zlibrary.util.Toaster;
import com.zhangbo.zlibrary.widget.LoadingDialog;

/**
 * Author: zhangbo
 * Data：2019/3/4
 * TODO: 用于提交数据的Present类，利用Dialog处理网络请求时的UI交互
 */
public class SubmitPresent extends BasePresent {

    Context mContext;
    LoadingDialog mLoadingDialog;

    public SubmitPresent(Context context) {
        mContext = context;
        mLoadingDialog = new LoadingDialog(context);
    }


    @Override
    public void refresh() {
        super.refresh();
    }

    @Override
    public void onBefore() {
        if(mLoadingDialog!=null){
            mLoadingDialog.show();
        }
    }

    @Override
    public void onSuccess(Object data) {
        if(mLoadingDialog!=null){
            mLoadingDialog.dismiss();
        }
        super.onSuccess(data);
    }

    @Override
    public void onFail(int code, String msg) {
        if(mLoadingDialog!=null){
            mLoadingDialog.dismiss();
        }
        if(mIView!=null && mIView instanceof SubmitCallBack){
            ((SubmitCallBack)mIView).fail(code,msg);
        }else{
            Toaster.show(mContext,msg);
        }
    }


    @Override
    public void dettchView() {
        super.dettchView();
        mLoadingDialog = null;
        mContext = null;
    }
}
