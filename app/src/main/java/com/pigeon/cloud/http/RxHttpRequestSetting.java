package com.pigeon.cloud.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.pigeon.basic.core.common.Config;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;

import com.pigeon.basic.core.utils.DebugUtils;
import per.goweii.rxhttp.request.setting.DefaultRequestSetting;
import per.goweii.rxhttp.request.utils.HttpsCompat;

/**
 * 描述：
 *
 * @author yangzhikuan
 * @date 2018/10/17
 */
public class RxHttpRequestSetting extends DefaultRequestSetting {

    private final PersistentCookieJar mCookieJar;

    public RxHttpRequestSetting(PersistentCookieJar cookieJar) {
        mCookieJar = cookieJar;
    }

    @Override
    public boolean isDebug() {
        return DebugUtils.isDebug();
    }

    @Override
    public long getTimeout() {
        return Config.HTTP_TIMEOUT;
    }

    @NonNull
    @Override
    public String getBaseUrl() {
        return WanApi.ApiConfig.BASE_URL;
    }

    @Nullable
    @Override
    public Map<String, String> getStaticHeaderParameter() {
        Map<String, String> map = new HashMap<>(1);
        map.put("Cache-Control", CacheControl.FORCE_NETWORK.toString());
        return map;
    }

    @Override
    public int getSuccessCode() {
        return 0;
    }

    @Override
    public void setOkHttpClient(OkHttpClient.Builder builder) {
        super.setOkHttpClient(builder);
        HttpsCompat.enableTls12ForOkHttp(builder);
        HttpsCompat.enableTls12ForHttpsURLConnection();
        builder.cookieJar(mCookieJar);
    }
}
