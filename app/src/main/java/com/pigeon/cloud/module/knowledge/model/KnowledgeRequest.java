package com.pigeon.cloud.module.knowledge.model;

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
public class KnowledgeRequest extends BaseRequest {

    public static void getKnowledgeListCache(@NonNull RequestListener<List<ChapterBean>> listener) {
        cacheList(WanCache.CacheKey.KNOWLEDGE_LIST,
                ChapterBean.class,
                listener);
    }

    public static void getKnowledgeList(RxLife rxLife, @NonNull RequestListener<List<ChapterBean>> listener) {
        cacheAndNetList(rxLife,
                WanApi.api().getKnowledgeList(),
                true,
                WanCache.CacheKey.KNOWLEDGE_LIST,
                ChapterBean.class,
                listener);
    }

    public static void getKnowledgeListCacheAndNet(RxLife rxLife, @NonNull RequestListener<List<ChapterBean>> listener) {
        cacheAndNetList(rxLife,
                WanApi.api().getKnowledgeList(),
                false,
                WanCache.CacheKey.KNOWLEDGE_LIST,
                ChapterBean.class,
                listener);
    }

    public static void getKnowledgeArticleListCache(int id, @IntRange(from = 0) int page, @NonNull RequestListener<ArticleListBean> listener) {
        cacheBean(WanCache.CacheKey.CHAPTER_ARTICLE_LIST(id, page, 0),
                ArticleListBean.class,
                listener);
    }

    public static void getKnowledgeArticleList(RxLife rxLife, boolean refresh, int id, @IntRange(from = 0) int page, @NonNull RequestListener<ArticleListBean> listener) {
        if (page == 0) {
            cacheAndNetBean(rxLife,
                    WanApi.api().getChapterArticleList(page, id, 0),
                    refresh,
                    WanCache.CacheKey.CHAPTER_ARTICLE_LIST(id, page, 0),
                    ArticleListBean.class,
                    listener);
        } else {
            rxLife.add(request(WanApi.api().getChapterArticleList(page, id, 0), listener));
        }
    }

}
