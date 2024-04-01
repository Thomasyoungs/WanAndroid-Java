package com.pigeon.cloud.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pigeon.basic.core.utils.SPUtils;

import com.pigeon.cloud.module.main.model.ConfigBean;

/**
 * @author yangzhikuan
 * @date 2019/5/18
 *
 */
public class ConfigUtils {
    private static final String SP_NAME = "config";
    private static final String KEY_CONFIG = "KEY_CONFIG";

    private final SPUtils mSPUtils = SPUtils.newInstance(SP_NAME);
    private final Gson mGson = new Gson();

    private ConfigBean mConfigBean = null;

    private static class Holder {
        private static final ConfigUtils INSTANCE = new ConfigUtils();
    }

    public static ConfigUtils getInstance() {
        return Holder.INSTANCE;
    }

    private ConfigUtils() {
    }

    @NonNull
    public ConfigBean getConfig() {
        if (mConfigBean != null) {
            return mConfigBean;
        }
        String json = mSPUtils.get(KEY_CONFIG, "");
        try {
            mConfigBean = mGson.fromJson(json, ConfigBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (mConfigBean == null) {
            setConfig(null);
        }
        return mConfigBean;
    }

    public void setConfig(@Nullable ConfigBean configBean) {
        if (configBean == null) {
            mConfigBean = new ConfigBean();
        } else {
            mConfigBean = configBean;
        }
        mSPUtils.save(KEY_CONFIG, mGson.toJson(mConfigBean));
        GrayFilterHelper.INSTANCE.refresh();
    }

    @Nullable
    public String getThemeName() {
        ConfigBean configBean = getConfig();
        if (configBean.isEnableAtNow() && !TextUtils.isEmpty(configBean.getTheme())) {
            return configBean.getTheme();
        }
        return null;
    }

    public int getTheme() {
        return ThemeUtils.getTheme(getThemeName());
    }

}
