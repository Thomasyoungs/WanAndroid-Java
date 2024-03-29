package com.pigeon.cloud.http;

import per.goweii.rxhttp.request.base.BaseResponse;

/**
 * @author yangzhikuan
 * @date 2019/5/8
 *
 */
public class WanResponse<E> implements BaseResponse<E> {

    private int errorCode;
    private String errorMsg;
    private E data;

    @Override
    public int getCode() {
        return errorCode;
    }

    @Override
    public void setCode(int code) {
        errorCode = code;
    }

    @Override
    public E getData() {
        return data;
    }

    @Override
    public void setData(E data) {
        this.data = data;
    }

    @Override
    public String getMsg() {
        return errorMsg;
    }

    @Override
    public void setMsg(String msg) {
        errorMsg = msg;
    }
}
