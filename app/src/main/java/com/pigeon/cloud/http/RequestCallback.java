package com.pigeon.cloud.http;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 * 
 */
public abstract class RequestCallback<E> implements RequestListener<E> {
    @Override
    public void onStart() {
    }

    @Override
    public void onError(ExceptionHandle handle) {
    }

    @Override
    public void onFinish() {
    }
}