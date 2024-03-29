package com.pigeon.cloud.utils.cdkey;

import androidx.annotation.NonNull;

/**
 * @author yangzhikuan
 * @date 2020/1/1
 * 
 */
public interface CDKey {
    @NonNull
    String create(@NonNull String userId);

    boolean check(@NonNull String userId, @NonNull String cdkey);
}
