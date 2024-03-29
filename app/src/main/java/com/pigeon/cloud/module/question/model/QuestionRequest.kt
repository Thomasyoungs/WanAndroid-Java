package com.pigeon.cloud.module.question.model

import androidx.annotation.IntRange
import per.goweii.rxhttp.core.RxLife
import com.pigeon.cloud.http.BaseRequest
import com.pigeon.cloud.http.RequestListener
import com.pigeon.cloud.http.WanApi
import com.pigeon.cloud.http.WanCache
import com.pigeon.cloud.module.main.model.ArticleListBean

/**
 * @author yangzhikuan
 * @date 2019/5/16
 * 
 */
object QuestionRequest : BaseRequest() {
    fun getQuestionListCache(@IntRange(from = 1) page: Int,
                             listener: RequestListener<ArticleListBean>
    ) {
        cacheBean(
            WanCache.CacheKey.QUESTION_LIST(page),
                ArticleListBean::class.java,
                listener)
    }

    fun getQuestionList(rxLife: RxLife,
                        @IntRange(from = 1) page: Int,
                        listener: RequestListener<ArticleListBean>
    ) {
        if (page == 1) {
            netBean(rxLife,
                    WanApi.api().getQuestionList(page),
                    WanCache.CacheKey.QUESTION_LIST(page),
                    listener)
        } else {
            rxLife.add(request(WanApi.api().getQuestionList(page), listener))
        }
    }
}