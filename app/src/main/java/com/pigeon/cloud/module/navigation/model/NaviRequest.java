package com.pigeon.cloud.module.navigation.model;

import androidx.annotation.NonNull;

import java.util.List;

import per.goweii.rxhttp.core.RxLife;
import com.pigeon.cloud.http.BaseRequest;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.http.WanApi;
import com.pigeon.cloud.http.WanCache;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public class NaviRequest extends BaseRequest {

    public static void getNaviListCache(@NonNull RequestListener<List<NaviBean>> listener) {
        cacheList(WanCache.CacheKey.NAVI_LIST,
                NaviBean.class,
                listener);
    }

    public static void getNaviList(RxLife rxLife, @NonNull RequestListener<List<NaviBean>> listener) {
        cacheAndNetList(rxLife,
                WanApi.api().getNaviList(),
                true,
                WanCache.CacheKey.NAVI_LIST,
                NaviBean.class,
                listener);
    }

}
