package com.pigeon.basic.core;

import android.app.Activity;

import com.pigeon.basic.utils.listener.SimpleCallback;

/**
 * 描述：
 *
 * @author yangzhikuan
 * @date 2019/4/27
 */
public class CoreInit {

    private SimpleCallback<Activity> mOnGoLoginCallback = null;

    private static class Holder {
        private static final CoreInit INSTANCE = new CoreInit();
    }

    private CoreInit() {
    }

    public static CoreInit getInstance() {
        return Holder.INSTANCE;
    }

    public void setOnGoLoginCallback(SimpleCallback<Activity> callback) {
        mOnGoLoginCallback = callback;
    }

    public SimpleCallback<Activity> getOnGoLoginCallback() {
        return mOnGoLoginCallback;
    }
}
