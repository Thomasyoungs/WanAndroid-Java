package com.pigeon.cloud.module.main.activity;

import androidx.annotation.Nullable;

import com.pigeon.basic.core.base.BaseActivity;
import com.pigeon.basic.core.mvp.MvpPresenter;
import com.pigeon.basic.core.base.BaseActivity;
import com.pigeon.basic.core.mvp.MvpPresenter;
import com.pigeon.cloud.R;
import com.pigeon.basic.core.base.BaseActivity;
import com.pigeon.basic.core.mvp.MvpPresenter;

/**
 * @author yangzhikuan
 * @date 2019/5/7
 * 
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        // return R.layout.activity_splash;
        return 0;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        MainActivity.start(getContext());
        finish();
        overridePendingTransition(R.anim.zoom_small_in, R.anim.zoom_small_out);
    }

    @Override
    protected void loadData() {
    }
}
