package com.pigeon.basic.core.glide.progress;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import okhttp3.OkHttpClient;
import com.pigeon.basic.core.utils.LogUtils;
import per.goweii.rxhttp.request.utils.HttpsCompat;

/**
 * 描述：
 *
 * @author yangzhikuan
 * @date 2018/9/17
 */
@GlideModule
public class ProgressAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(getOkHttpClient()));
        LogUtils.d("ProgressAppGlideModule", "registerComponents down");
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    private static OkHttpClient getOkHttpClient() {
        LogUtils.d("ProgressAppGlideModule", "getOkHttpClient");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpsCompat.ignoreSSLForOkHttp(builder);
        HttpsCompat.enableTls12ForOkHttp(builder);
        builder.addInterceptor(new ProgressInterceptor());
        LogUtils.d("ProgressAppGlideModule", "getOkHttpClient down");
        return builder.build();
    }
}
