package com.pigeon.cloud.module.book.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.pigeon.cloud.http.BaseRequest;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.http.WanApi;
import com.pigeon.cloud.http.WanCache;

import java.util.List;

import per.goweii.rxhttp.core.RxLife;
import com.pigeon.cloud.http.BaseRequest;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.http.WanApi;
import com.pigeon.cloud.http.WanCache;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.cloud.http.BaseRequest;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.http.WanApi;
import com.pigeon.cloud.http.WanCache;

public class BookRequest extends BaseRequest {
    public static void getBookList(RxLife rxLife, @NonNull RequestListener<List<BookBean>> listener) {
        cacheAndNetList(rxLife,
                WanApi.api().getBooks(),
                false,
                WanCache.CacheKey.BOOK_LIST,
                BookBean.class,
                listener);
    }

    public static void getBookChapterList(RxLife rxLife, int id, @IntRange(from = 0) int page, @NonNull RequestListener<ArticleListBean> listener) {
        if (page == 0) {
            cacheAndNetBean(rxLife,
                    WanApi.api().getChapterArticleList(page, id, 1),
                    false,
                    WanCache.CacheKey.CHAPTER_ARTICLE_LIST(id, page, 1),
                    ArticleListBean.class,
                    listener);
        } else {
            rxLife.add(request(WanApi.api().getChapterArticleList(page, id, 1), listener));
        }
    }

}
