package com.zhangbo.zlibrary.rxwraper;

import android.os.NetworkOnMainThreadException;

import com.zhangbo.zlibrary.util.LogUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Author: zhangbo
 * Data：2019/1/2
 * TODO:
 */
public abstract class BaseObserver<T> implements Observer<IResult<T>> {

    protected int RESPONSE_SUCCESS = 200;
    private int RESPONSE_ERROR = -1;
    private int errorCode;
    private String errorMsg = "服务器未知异常";//无敌甩锅术~~

    protected Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(IResult t) {
        disposeIt();
        if (t.getCode()==RESPONSE_SUCCESS) {
            onSuccess((T)t.getData());
        } else {
            onFail(t.getCode(), t.getMessage());
        }
    }

    @Override
    public void onError(Throwable t) {
        LogUtils.e(t.getMessage() + "");
        disposeIt();
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            errorCode = httpException.code();
            errorMsg = httpException.getMessage();
            //getErrorMsg(httpException);
        } else if (t instanceof SocketTimeoutException) {  //VPN open
            errorCode = RESPONSE_ERROR;
            errorMsg = "服务器响应超时";
        } else if (t instanceof ConnectException) {
            errorCode = RESPONSE_ERROR;
            errorMsg = "网络连接异常，请检查网络";
        } else if (t instanceof UnknownHostException) {
            errorCode = RESPONSE_ERROR;
            errorMsg = "无法解析主机，请检查网络连接";
        } else if (t instanceof UnknownServiceException) {
            errorCode = RESPONSE_ERROR;
            errorMsg = "未知的服务器错误";
        } else if (t instanceof IOException) {   //飞行模式等
            errorCode = RESPONSE_ERROR;
            errorMsg = "没有网络，请检查网络连接";
        } else if (t instanceof NetworkOnMainThreadException) {
            //主线程不能网络请求，这个很容易发现
            errorCode = RESPONSE_ERROR;
            errorMsg = "主线程不能网络请求";
            // ... ...
        } else if (t instanceof RuntimeException) {
            //很多的错误都是extends RuntimeException
            errorCode = RESPONSE_ERROR;
            errorMsg = "运行时错误" + t.toString();
        }
        onFail(errorCode, errorMsg);
    }

    @Override
    public void onComplete() {
        disposeIt();
    }


    /**
     * 销毁disposable
     */
    private void disposeIt() {
        //RxHttpTipLoadDialog.getHttpTipLoadDialog().dismissDialog();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }

    public abstract void onSuccess(T t);

    public void onFail(int code, String message){

    }

}
