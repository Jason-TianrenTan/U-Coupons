package com.example.administrator.ccoupons.Connections;

/**
 * Created by Administrator on 2017/8/17 0017.
 */

public interface OnEventLister<T> {
    void onSuccess(T response);

    void onFail(String errCode, String errMsg);
}