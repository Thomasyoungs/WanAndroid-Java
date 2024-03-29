package com.pigeon.cloud.module.login.model;

import androidx.annotation.NonNull;

import io.reactivex.disposables.Disposable;
import com.pigeon.cloud.http.BaseRequest;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.http.WanApi;

/**
 * @author yangzhikuan
 * @date 2019/5/16
 * 
 */
public class LoginRequest extends BaseRequest {

    public static Disposable login(String userName, String password, @NonNull RequestListener<LoginBean> listener) {
        return request(WanApi.api().login(userName, password), listener);
    }

    public static Disposable register(String userName, String password, String repassword, @NonNull RequestListener<LoginBean> listener) {
        return request(WanApi.api().register(userName, password, repassword), listener);
    }
}
