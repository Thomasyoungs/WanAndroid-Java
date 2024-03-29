package com.pigeon.cloud.module.main.activity;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.pigeon.basic.core.base.BaseActivity;
import com.pigeon.basic.core.mvp.MvpPresenter;
import com.pigeon.basic.core.base.BaseActivity;
import com.pigeon.basic.core.mvp.MvpPresenter;
import com.pigeon.basic.core.base.BaseActivity;
import com.pigeon.basic.core.mvp.MvpPresenter;

public class BringToFrontActivity extends BaseActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, BringToFrontActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        finish();
    }

    @Override
    protected void loadData() {
    }
}
