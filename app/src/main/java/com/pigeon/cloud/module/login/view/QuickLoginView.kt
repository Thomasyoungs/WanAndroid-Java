package com.pigeon.cloud.module.login.view

import com.pigeon.basic.core.base.BaseView
import com.pigeon.cloud.module.login.model.UserEntity

interface QuickLoginView : BaseView {
    fun loginSuccess(code: Int, data: UserEntity)
    fun loginFailed(code: Int, msg: String?)
}