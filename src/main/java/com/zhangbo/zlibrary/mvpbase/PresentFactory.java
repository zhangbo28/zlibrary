package com.zhangbo.zlibrary.mvpbase;

/**
 * Author: zhangbo
 * Data：2019/3/4
 * TODO:
 */
public class PresentFactory {

    public static final int SUBMIT = 0;
    public static final int LOAD = 1;
    public static final int LIST = 2;

    public static IPresent create(int type){
        IPresent iPresent = null;
        if(type==SUBMIT){
            iPresent = new SubmitPresent(null);
        }
        return iPresent;
    }

}
