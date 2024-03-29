package com.pigeon.cloud.utils.web.cache

import per.goweii.rxhttp.request.RxRequest
import com.pigeon.cloud.http.WanApi
import com.pigeon.cloud.module.main.model.WebArticleUrlRegexBean

object ReadingModeManager {
    private val urlRegexList = arrayListOf<WebArticleUrlRegexBean>()

    fun setup() {
        RxRequest.create(WanApi.api().webArticleUrlRegex)
                .request(object : RxRequest.ResultCallback<List<WebArticleUrlRegexBean>> {
                    override fun onSuccess(code: Int, data: List<WebArticleUrlRegexBean>) {
                        urlRegexList.clear()
                        urlRegexList.addAll(data)
                    }

                    override fun onFailed(code: Int, msg: String) {
                    }
                })
    }

    fun getUrlRegexBeanForHost(host: String?): WebArticleUrlRegexBean? {
        if (urlRegexList.isEmpty()) return null
        host ?: return null
        return urlRegexList.firstOrNull { host.contains(it.host) }
    }
}