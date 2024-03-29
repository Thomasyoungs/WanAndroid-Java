package com.pigeon.cloud.module.mine.contract

import com.pigeon.basic.core.base.BasePresenter
import com.pigeon.basic.core.base.BaseView
import com.pigeon.cloud.module.login.model.UserEntity
import com.pigeon.cloud.utils.UserUtils

/**
 * @author yangzhikuan
 * @date 2020/5/27
 */
interface UserInfoView : BaseView {
    fun gotoLogin()
    fun mineInfoSuccess(userEntity: UserEntity)
}

class UserInfoPresenter : BasePresenter<UserInfoView>() {
    fun mineInfo() {
        if (!UserUtils.getInstance().isLogin) {
            if (isAttach) {
                baseView.gotoLogin()
            }
            return
        }
        UserUtils.getInstance().loginUser.let {
            if (isAttach) {
                baseView.mineInfoSuccess(it)
            }
        }
    }
}
