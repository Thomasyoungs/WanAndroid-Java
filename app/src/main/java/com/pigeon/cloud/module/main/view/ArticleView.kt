package com.pigeon.cloud.module.main.view

import com.pigeon.basic.core.base.BaseView

/**
 * @author yangzhikuan
 * @date 2020/2/20
 */
interface ArticleView : BaseView {
    fun collectSuccess()
    fun collectFailed(msg: String)
    fun uncollectSuccess()
    fun uncollectFailed(msg: String)
    fun addReadLaterSuccess()
    fun addReadLaterFailed()
    fun removeReadLaterSuccess()
    fun removeReadLaterFailed()
}