package com.pigeon.cloud.utils.web.interceptor

import android.net.Uri
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.pigeon.cloud.utils.web.cache.ImageCacheManager
import com.pigeon.cloud.utils.web.cache.ResCacheManager

/**
 * @author yangzhikuan
 * @date 2020/2/25
 */
object WebResUrlInterceptor : BaseWebUrlInterceptor() {
    override fun intercept(
        pageUri: Uri,
        uri: Uri,
        userAgent: String?,
        reqHeaders: Map<String, String>?,
        reqMethod: String?
    ): WebResourceResponse? {
        ResCacheManager.get(uri, userAgent, reqHeaders, reqMethod)?.let { return it }
        ImageCacheManager.get(uri, userAgent, reqHeaders, reqMethod)?.let { return it }
        return null
    }
}