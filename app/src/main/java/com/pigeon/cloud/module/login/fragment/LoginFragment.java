package com.pigeon.cloud.module.login.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.pigeon.basic.core.base.BaseFragment;
import com.pigeon.basic.core.toast.ToastMaker;
import com.pigeon.basic.core.utils.InputMethodUtils;
import com.pigeon.cloud.R;
import com.pigeon.cloud.event.LoginEvent;
import com.pigeon.cloud.module.login.activity.AuthActivity;
import com.pigeon.cloud.module.login.model.UserEntity;
import com.pigeon.cloud.module.login.presenter.LoginPresenter;
import com.pigeon.cloud.module.login.view.LoginView;
import com.pigeon.cloud.widget.InputView;
import com.pigeon.cloud.widget.PasswordInputView;
import com.pigeon.cloud.widget.SubmitView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author yangzhikuan
 * @date 2019/5/16
 *
 */
public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginView {

    @BindView(R.id.ll_go_register)
    LinearLayout ll_go_register;
    @BindView(R.id.piv_login_account)
    InputView piv_account;
    @BindView(R.id.piv_login_password)
    PasswordInputView piv_password;
    @BindView(R.id.sv_login)
    SubmitView sv_login;

    private AuthActivity mActivity;

    public static LoginFragment create() {
        return new LoginFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @Nullable
    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AuthActivity) context;
    }

    @Override
    protected void initView() {
        piv_password.setOnPwdFocusChangedListener(new PasswordInputView.OnPwdFocusChangedListener() {
            @Override
            public void onFocusChanged(boolean focus) {
                mActivity.doEyeAnim(focus);
            }
        });
    }

    @Override
    protected void loadData() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity.getSoftInputHelper().moveWith(sv_login,
                piv_account.getEditText(), piv_password.getEditText());
    }

    @Override
    public void onVisible(boolean isFirstVisible) {
        super.onVisible(isFirstVisible);
    }

    @Override
    public void onInvisible() {
        super.onInvisible();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.ll_go_register, R.id.sv_login})
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected boolean onClick1(View v) {
        switch (v.getId()) {
            default:
                return false;
            case R.id.ll_go_register:
                mActivity.changeToRegister();
                break;
            case R.id.sv_login:
                InputMethodUtils.hide(sv_login);
                String userName = piv_account.getText();
                String password = piv_password.getText();
                presenter.login(userName, password, false);
                break;
        }
        return true;
    }

    public void loginByBiometric(String username, String password) {
        presenter.login(username, password, true);
    }

    @Override
    public void loginSuccess(int code, UserEntity data, String username, String password, boolean isBiometric) {
        new LoginEvent(true).post();
        if (isBiometric) {
            finish();
        } else {
            mActivity.tryOpenLoginByBiometric(username, password);
        }
    }

    @Override
    public void loginFailed(int code, String msg) {
        ToastMaker.showShort(msg);
    }
}
