package com.pigeon.basic.core.utils;

import android.text.TextUtils;

import com.pigeon.basic.core.BuildConfig;

/**
 * @author yangzhikuan
 * @date 2019/10/20
 *
 */
public class DebugUtils {

    private static final boolean DEBUG;

    static {
        DEBUG = BuildConfig.DEBUG && TextUtils.equals(BuildConfig.BUILD_TYPE, "debug");
    }

    public static boolean isDebug() {
        return DEBUG;
    }

}
