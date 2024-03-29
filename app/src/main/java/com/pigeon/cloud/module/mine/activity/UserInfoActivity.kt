package com.pigeon.cloud.module.mine.activity

import android.content.Context
import android.content.Intent
import kotlinx.android.synthetic.main.activity_user_info.*
import com.pigeon.basic.core.base.BaseActivity
import com.pigeon.cloud.R
import com.pigeon.cloud.event.UserInfoUpdateEvent
import com.pigeon.cloud.module.login.activity.AuthActivity
import com.pigeon.cloud.module.login.model.UserEntity
import com.pigeon.cloud.module.mine.contract.UserInfoPresenter
import com.pigeon.cloud.module.mine.contract.UserInfoView
import com.pigeon.cloud.utils.ImageLoader

/**
 * @author yangzhikuan
 * @date 2020/5/27
 */
class UserInfoActivity : BaseActivity<UserInfoPresenter>(), UserInfoView {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, UserInfoActivity::class.java))
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_user_info

    override fun initPresenter(): UserInfoPresenter = UserInfoPresenter()

    override fun initView() {
    }

    override fun loadData() {
        presenter.mineInfo()
    }

    override fun gotoLogin() {
        AuthActivity.startQuickLogin(context)
        finish()
    }

    override fun mineInfoSuccess(userEntity: UserEntity) {
        UserInfoUpdateEvent().post()
        ImageLoader.userIcon(civ_user_icon, userEntity.avatar ?: "")
        ImageLoader.userBlur(iv_blur, userEntity.cover ?: "")
        tv_user_name.text = userEntity.username
        tv_user_id.text = userEntity.wanid.toString()
    }
}