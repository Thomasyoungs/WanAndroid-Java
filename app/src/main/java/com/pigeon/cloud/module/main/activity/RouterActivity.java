package com.pigeon.cloud.module.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pigeon.basic.core.base.BaseActivity;
import com.pigeon.basic.core.mvp.MvpPresenter;
import com.pigeon.basic.core.utils.LogUtils;
import com.pigeon.cloud.common.WanApp;
import com.pigeon.cloud.utils.UrlOpenUtils;

import per.goweii.swipeback.SwipeBackAbility;
import per.goweii.swipeback.SwipeBackDirection;

/**
 * @author yangzhikuan
 * @date 2019/5/7
 *
 */
public class RouterActivity extends BaseActivity implements SwipeBackAbility.Direction, Runnable {

    private static final String TAG = RouterActivity.class.getSimpleName();

    @NonNull
    @Override
    public SwipeBackDirection swipeBackDirection() {
        return SwipeBackDirection.NONE;
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

    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity ma = WanApp.findActivity(MainActivity.class);
        LogUtils.i(TAG, "findActivity=" + ma);
        if (ma == null) {
            LogUtils.i(TAG, "start MainActivity");
            MainActivity.start(getContext());
            afterMainActivityStarted();
        } else {
            parseIntent(getIntent());
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacks(this);
        }
        super.onDestroy();
    }

    private Handler mHandler = null;

    private void afterMainActivityStarted() {
        mHandler = new Handler();
        mHandler.postDelayed(this, 100);
    }

    @Override
    public void run() {
        Activity ma = WanApp.findActivity(MainActivity.class);
        LogUtils.i(TAG, "findActivity=" + ma);
        if (ma == null) {
            mHandler.postDelayed(this, 100);
        } else {
            parseIntent(getIntent());
            finish();
        }
    }

    private void parseIntent(Intent intent) {
        String action = intent.getAction();
        LogUtils.d(TAG, "action=" + intent.getAction());
        if (action == null) {
            return;
        }
        switch (action) {
            default:
                break;
            case Intent.ACTION_VIEW:
                handleOpenUrl(intent.getData());
                break;
            case Intent.ACTION_SEND:
                handleShareText(intent.getStringExtra(Intent.EXTRA_TEXT));
                break;
        }
    }

    private void handleOpenUrl(Uri data) {
        LogUtils.d(TAG, "data=" + data);
        if (data == null) {
            return;
        }
        String scheme = data.getScheme();
        if (!TextUtils.equals(scheme, "http") && !TextUtils.equals(scheme, "https")) {
            return;
        }
        UrlOpenUtils.Companion
                .with(data.toString())
                .open(getContext());
    }

    private void handleShareText(String sharedText) {
        try {
            LogUtils.d(TAG, "sharedText=" + sharedText);
            if (TextUtils.isEmpty(sharedText)) {
                return;
            }
            int urlStartIndex = sharedText.indexOf("https://");
            if (urlStartIndex < 0) {
                urlStartIndex = sharedText.indexOf("http://");
            }
            if (urlStartIndex < 0) {
                return;
            }
            String msg = "";
            if (urlStartIndex > 0) {
                msg = sharedText.substring(0, urlStartIndex - 1);
            }
            LogUtils.d(TAG, "sharedMsg=" + msg);
            StringBuilder urlBuilder = new StringBuilder();
            for (int i = urlStartIndex; i < sharedText.length(); i++) {
                char c = sharedText.charAt(i);
                if (c == ' ') {
                    break;
                }
                urlBuilder.append(c);
            }
            String url = urlBuilder.toString();
            LogUtils.d(TAG, "sharedUrl=" + url);
            ShareArticleActivity.start(getContext(), msg, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
