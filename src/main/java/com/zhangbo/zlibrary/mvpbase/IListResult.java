package com.zhangbo.zlibrary.mvpbase;

import java.util.List;

/**
 * Author: zhangbo
 * Data：2019/1/2
 * TODO: 业务代码中 列表类的响应实体对象须实现给接口
 */
public interface IListResult<T> {

    List<T> getList();

    int getCount();
}
