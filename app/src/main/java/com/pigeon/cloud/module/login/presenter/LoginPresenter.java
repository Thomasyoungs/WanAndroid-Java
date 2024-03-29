package com.pigeon.cloud.module.login.presenter;

import com.pigeon.cloud.module.login.model.LoginBean;
import com.pigeon.cloud.module.login.model.LoginRequest;
import com.pigeon.cloud.module.login.view.LoginView;
import com.pigeon.basic.core.base.BasePresenter;
import per.goweii.rxhttp.request.exception.ExceptionHandle;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.login.model.LoginBean;
import com.pigeon.cloud.module.login.model.LoginRequest;
import com.pigeon.cloud.module.login.view.LoginView;
import com.pigeon.cloud.utils.UserUtils;
import com.pigeon.cloud.module.login.model.LoginBean;
import com.pigeon.cloud.module.login.model.LoginRequest;
import com.pigeon.cloud.module.login.view.LoginView;

/**
 * @author yangzhikuan
 * @date 2019/5/15
 *
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    public void login(String username, String password, boolean isBiometric) {
        addToRxLife(LoginRequest.login(username, password, new RequestListener<LoginBean>() {
            @Override
            public void onStart() {
                showLoadingDialog();
            }

            @Override
            public void onSuccess(int code, LoginBean data) {
                UserUtils.getInstance().login(data);
                if (isAttach()) {
                    getBaseView().loginSuccess(0, UserUtils.getInstance().getLoginUser(), username, password, isBiometric);
                }
                dismissLoadingDialog();
            }

            @Override
            public void onFailed(int code, String msg) {
                UserUtils.getInstance().logout();
                if (isAttach()) {
                    getBaseView().loginFailed(code, msg);
                }
                dismissLoadingDialog();
            }

            @Override
            public void onError(ExceptionHandle handle) {

            }

            @Override
            public void onFinish() {
            }
        }));
    }
}
