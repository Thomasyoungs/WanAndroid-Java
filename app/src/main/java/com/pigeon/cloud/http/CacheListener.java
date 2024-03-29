package com.pigeon.cloud.http;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public interface CacheListener<E> {
    void onSuccess(int code, E data);

    void onFailed();
}
