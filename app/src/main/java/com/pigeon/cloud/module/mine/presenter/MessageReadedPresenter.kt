package com.pigeon.cloud.module.mine.presenter

import com.google.gson.stream.MalformedJsonException
import com.pigeon.basic.core.base.BasePresenter
import per.goweii.rxhttp.request.exception.ExceptionHandle
import com.pigeon.cloud.http.RequestListener
import com.pigeon.cloud.http.WanApi
import com.pigeon.cloud.module.main.model.ListBean
import com.pigeon.cloud.module.mine.model.MessageBean
import com.pigeon.cloud.module.mine.model.MineRequest
import com.pigeon.cloud.module.mine.view.MessageReadedView

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
class MessageReadedPresenter : BasePresenter<MessageReadedView>() {

    fun getMessageReadList(page: Int) {
        addToRxLife(MineRequest.getMessageReadList(page, object :
            RequestListener<ListBean<MessageBean>> {
            override fun onStart() {}
            override fun onSuccess(code: Int, data: ListBean<MessageBean>) {
                if (isAttach) {
                    baseView.getMessageReadListSuccess(code, data)
                }
            }

            override fun onFailed(code: Int, msg: String) {
                if (isAttach) {
                    baseView.getMessageReadListFail(code, msg)
                }
            }

            override fun onError(handle: ExceptionHandle) {}
            override fun onFinish() {}
        }))
    }

    fun delete(item: MessageBean) {
        addToRxLife(MineRequest.deleteMessage(item.id, object :
            RequestListener<Any?> {
            override fun onStart() {}
            override fun onSuccess(code: Int, data: Any?) {
                if (isAttach) {
                    baseView.deleteMessageSuccess(code, item)
                }
            }

            override fun onFailed(code: Int, msg: String) {
                if (isAttach) {
                    baseView.deleteMessageFail(code, msg)
                }
            }

            override fun onError(handle: ExceptionHandle) {
                if (handle.exception is MalformedJsonException) {
                    if (isAttach) {
                        baseView.deleteMessageSuccess(WanApi.ApiCode.SUCCESS, item)
                    }
                }
            }

            override fun onFinish() {}
        }))
    }

}