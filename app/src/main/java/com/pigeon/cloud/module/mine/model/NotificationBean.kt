package com.pigeon.cloud.module.mine.model

/**
 * @author yangzhikuan
 * @date 2020/5/16
 */
data class NotificationBean(
        val tags: List<String>,
        val aniceDate: String,
        val fromUser: String,
        val articleContent: String,
        val content: String,
        val articleUrl: String,
        val deleteUrl: String
)