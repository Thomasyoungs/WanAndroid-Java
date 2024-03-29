package com.pigeon.cloud.module.mine.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import com.pigeon.basic.core.base.BasePresenter
import per.goweii.rxhttp.request.exception.ExceptionHandle
import com.pigeon.cloud.http.RequestListener
import com.pigeon.cloud.module.main.model.ListBean
import com.pigeon.cloud.module.mine.model.MessageBean
import com.pigeon.cloud.module.mine.model.MineRequest
import com.pigeon.cloud.module.mine.view.MessageUnreadView

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
class MessageUnreadPresenter : BasePresenter<MessageUnreadView>(), CoroutineScope by MainScope() {

    fun getMessageUnreadList(page: Int) {
        addToRxLife(MineRequest.getMessageUnreadList(page, object :
            RequestListener<ListBean<MessageBean>> {
            override fun onStart() {}
            override fun onSuccess(code: Int, data: ListBean<MessageBean>) {
                if (isAttach) {
                    baseView.getMessageUnreadListSuccess(code, data)
                }
            }

            override fun onFailed(code: Int, msg: String) {
                if (isAttach) {
                    baseView.getMessageUnreadListFail(code, msg)
                }
            }

            override fun onError(handle: ExceptionHandle) {}
            override fun onFinish() {}
        }))
    }

}