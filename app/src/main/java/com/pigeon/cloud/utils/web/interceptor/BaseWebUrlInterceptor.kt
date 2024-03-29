package com.pigeon.cloud.utils.web.interceptor

import okhttp3.Call
import com.pigeon.cloud.utils.web.interceptor.WebHttpClient.stringRespBody

/**
 * @author yangzhikuan
 * @date 2020/2/25
 */
abstract class BaseWebUrlInterceptor : WebUrlInterceptor {
    private val callList = arrayListOf<Call>()

    fun Call.resp(): String? {
        callList.add(this)
        val resp = stringRespBody()
        callList.remove(this)
        return resp
    }

    override fun cancel() {
        callList.forEach { it.cancel() }
        callList.clear()
    }
}