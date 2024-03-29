package com.pigeon.cloud.http;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public interface RequestListener<E> {
    void onStart();

    void onSuccess(int code, E data);

    void onFailed(int code, String msg);

    void onError(ExceptionHandle handle);

    void onFinish();
}
