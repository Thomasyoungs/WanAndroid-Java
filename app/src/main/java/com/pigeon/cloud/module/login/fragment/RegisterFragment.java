package com.pigeon.cloud.module.login.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.pigeon.basic.core.base.BaseFragment;
import com.pigeon.basic.ui.toast.ToastMaker;
import com.pigeon.basic.utils.InputMethodUtils;
import com.pigeon.cloud.R;
import com.pigeon.cloud.event.LoginEvent;
import com.pigeon.cloud.module.login.activity.AuthActivity;
import com.pigeon.cloud.module.login.model.UserEntity;
import com.pigeon.cloud.module.login.presenter.RegisterPresenter;
import com.pigeon.cloud.module.login.view.RegisterView;
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
public class RegisterFragment extends BaseFragment<RegisterPresenter> implements RegisterView {

    @BindView(R.id.ll_go_login)
    LinearLayout ll_go_login;
    @BindView(R.id.piv_register_account)
    InputView piv_account;
    @BindView(R.id.piv_register_password)
    PasswordInputView piv_password;
    @BindView(R.id.piv_register_password_again)
    PasswordInputView piv_password_again;
    @BindView(R.id.sv_register)
    SubmitView sv_register;

    private AuthActivity mActivity;

    public static RegisterFragment create() {
        return new RegisterFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_register;
    }

    @Nullable
    @Override
    protected RegisterPresenter initPresenter() {
        return new RegisterPresenter();
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
        piv_password_again.setOnPwdFocusChangedListener(new PasswordInputView.OnPwdFocusChangedListener() {
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
        mActivity.getSoftInputHelper().moveWith(
                sv_register,
                piv_account.getEditText(),
                piv_password.getEditText(),
                piv_password_again.getEditText());
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

    @OnClick({R.id.ll_go_login, R.id.sv_register})
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected boolean onClick1(View v) {
        switch (v.getId()) {
            default:
                return false;
            case R.id.ll_go_login:
                mActivity.changeToLogin();
                break;
            case R.id.sv_register:
                InputMethodUtils.hide(sv_register);
                register();
                break;
        }
        return true;
    }

    private void register() {
        String password = piv_password.getText();
        String repassword = piv_password_again.getText();
        if (!TextUtils.equals(password, repassword)) {
            ToastMaker.showShort("请确认2次密码一致");
            return;
        }
        String username = piv_account.getText();
        presenter.register(username, password, repassword);
    }

    @Override
    public void registerSuccess(int code, UserEntity data, String username, String password) {
        new LoginEvent(true).post();
        mActivity.tryOpenLoginByBiometric(username, password);
        //finish();
    }

    @Override
    public void registerFailed(int code, String msg) {
        ToastMaker.showShort(msg);
    }
}
