package com.pigeon.cloud.module.mine.view

import com.pigeon.basic.core.base.BaseView
import com.pigeon.cloud.module.main.model.ListBean
import com.pigeon.cloud.module.mine.model.MessageBean

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
interface MessageUnreadView : BaseView {
    fun getMessageUnreadListSuccess(code: Int, data: ListBean<MessageBean>)
    fun getMessageUnreadListFail(code: Int, msg: String)
}