package com.pigeon.cloud.module.mine.view

import com.pigeon.basic.core.base.BaseView
import com.pigeon.cloud.module.main.model.ListBean
import com.pigeon.cloud.module.mine.model.MessageBean

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
interface MessageReadedView : BaseView {
    fun getMessageReadListSuccess(code: Int, data: ListBean<MessageBean>)
    fun getMessageReadListFail(code: Int, msg: String)
    fun deleteMessageSuccess(code: Int, data: MessageBean)
    fun deleteMessageFail(code: Int, msg: String)
}