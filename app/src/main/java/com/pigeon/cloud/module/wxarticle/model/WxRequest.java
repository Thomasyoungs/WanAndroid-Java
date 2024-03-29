package com.pigeon.cloud.module.wxarticle.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.util.List;

import per.goweii.rxhttp.core.RxLife;
import com.pigeon.cloud.http.BaseRequest;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.http.WanApi;
import com.pigeon.cloud.http.WanCache;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.cloud.module.main.model.ChapterBean;

/**
 * @author yangzhikuan
 * @date 2019/5/16
 *
 */
public class WxRequest extends BaseRequest {

    public static void getWxArticleChapters(RxLife rxLife, @NonNull RequestListener<List<ChapterBean>> listener) {
        cacheAndNetList(rxLife,
                WanApi.api().getWxArticleChapters(),
                WanCache.CacheKey.WXARTICLE_CHAPTERS,
                ChapterBean.class,
                listener);
    }

    public static void getWxArticleListCache(int id, @IntRange(from = 1) int page, @NonNull RequestListener<ArticleListBean> listener) {
        cacheBean(WanCache.CacheKey.WXARTICLE_LIST(id, page),
                ArticleListBean.class,
                listener);
    }

    public static void getWxArticleList(RxLife rxLife, boolean refresh, int id, @IntRange(from = 1) int page, @NonNull RequestListener<ArticleListBean> listener) {
        if (page == 1) {
            cacheAndNetBean(rxLife,
                    WanApi.api().getWxArticleList(id, page),
                    refresh,
                    WanCache.CacheKey.WXARTICLE_LIST(id, page),
                    ArticleListBean.class,
                    listener);
        } else {
            rxLife.add(request(WanApi.api().getWxArticleList(id, page), listener));
        }
    }

    public static void getWxArticleList(RxLife rxLife, boolean refresh, int id, @IntRange(from = 1) int page, String key, @NonNull RequestListener<ArticleListBean> listener) {
        if (page == 1) {
            cacheAndNetBean(rxLife,
                    WanApi.api().getWxArticleList(id, page, key),
                    refresh,
                    WanCache.CacheKey.WXARTICLE_LIST_SEARCH(id, page, key),
                    ArticleListBean.class,
                    listener);
        } else {
            rxLife.add(request(WanApi.api().getWxArticleList(id, page, key), listener));
        }
    }

}
