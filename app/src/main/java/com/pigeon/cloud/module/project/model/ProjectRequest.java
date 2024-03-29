package com.pigeon.cloud.module.project.model;

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
 * @date 2019/5/12
 * 
 */
public class ProjectRequest extends BaseRequest {

    public static void getProjectChapters(RxLife rxLife, @NonNull RequestListener<List<ChapterBean>> listener) {
        cacheAndNetList(rxLife,
                WanApi.api().getProjectChapters(),
                WanCache.CacheKey.PROJECT_CHAPTERS,
                ChapterBean.class,
                listener);
    }

    public static void getProjectArticleListCache(int id, @IntRange(from = 1) int page, @NonNull RequestListener<ArticleListBean> listener) {
        cacheBean(WanCache.CacheKey.PROJECT_ARTICLE_LIST(id, page),
                ArticleListBean.class,
                listener);
    }

    public static void getProjectArticleList(RxLife rxLife, boolean refresh, int id, @IntRange(from = 1) int page, @NonNull RequestListener<ArticleListBean> listener) {
        if (page == 1) {
            cacheAndNetBean(rxLife,
                    WanApi.api().getProjectArticleList(page, id),
                    refresh,
                    WanCache.CacheKey.PROJECT_ARTICLE_LIST(id, page),
                    ArticleListBean.class,
                    listener);
        } else {
            rxLife.add(request(WanApi.api().getProjectArticleList(page, id), listener));
        }
    }

}
