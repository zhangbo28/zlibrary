package com.zhangbo.zlibrary.rxwraper;

/**
 * Author: zhangbo
 * Data：2019/1/3
 * TODO:
 */
public interface IResult<T>{

    int getCode();

    String getMessage();

    T getData();

    boolean isSuccess();

}
