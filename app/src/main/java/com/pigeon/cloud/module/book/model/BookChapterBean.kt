package com.pigeon.cloud.module.book.model

import com.pigeon.cloud.module.main.model.ArticleBean

data class BookChapterBean(
    val articleBean: ArticleBean,
    var time: Long = 0L,
    var percent: Float = 0F,
)