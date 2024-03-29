package com.pigeon.cloud.module.login.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.login.model.UserEntity;

/**
 * @author yangzhikuan
 * @date 2019/5/15
 *
 */
public interface LoginView extends BaseView {
    void loginSuccess(int code, UserEntity data, String username, String password, boolean isBiometric);

    void loginFailed(int code, String msg);
}
