package com.pigeon.cloud.module.login.presenter

import com.pigeon.basic.core.base.BasePresenter
import com.pigeon.basic.core.utils.Base64Utils.decodeToBytes
import com.pigeon.basic.core.utils.Base64Utils.decodeToString
import com.pigeon.basic.core.utils.Base64Utils.encodeToBytes
import com.pigeon.basic.core.utils.Base64Utils.encodeToString
import com.pigeon.basic.core.utils.SPUtils
import per.goweii.rxhttp.request.exception.ExceptionHandle
import com.pigeon.cloud.http.RequestListener
import com.pigeon.cloud.module.login.model.LoginBean
import com.pigeon.cloud.module.login.model.LoginInfoEntity
import com.pigeon.cloud.module.login.model.LoginRequest
import com.pigeon.cloud.module.login.view.QuickLoginView
import com.pigeon.cloud.utils.UserUtils
import javax.crypto.Cipher

class QuickLoginPresenter : BasePresenter<QuickLoginView>() {
    private val spUtils = SPUtils.newInstance("authInfo")

    val loginInfo: String
        get() = spUtils.get("loginInfo", "")
    val cipherIV: String
        get() = spUtils.get("cipherIV", "")

    fun encodeAndSaveLoginInfo(cipher: Cipher, loginInfoEntity: LoginInfoEntity?): Boolean {
        return if (loginInfoEntity == null || loginInfoEntity.isEmpty) {
            false
        } else try {
            val loginInfoStr = loginInfoEntity.toJson()
            val loginInfoBytes = encodeToBytes(loginInfoStr)
            val loginInfoEncodeBytes = cipher.doFinal(loginInfoBytes)
            val loginInfoEncodeStr = encodeToString(loginInfoEncodeBytes)
            val ivBytes = cipher.iv
            val ivStr = encodeToString(ivBytes)
            spUtils.save(SP_KEY_LOGIN_INFO, loginInfoEncodeStr)
            spUtils.save(SP_KEY_CIPHER_IV, ivStr)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun decodeLoginInfo(cipher: Cipher, loginInfoStr: String?): LoginInfoEntity? {
        return try {
            val loginInfoBytes = decodeToBytes(loginInfoStr)
            val loginInfoDecodedBytes = cipher.doFinal(loginInfoBytes)
            val loginInfoJson = decodeToString(loginInfoDecodedBytes)
            LoginInfoEntity.fromJson(loginInfoJson)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun login(username: String, password: String) {
        addToRxLife(LoginRequest.login(username, password, object :
            RequestListener<LoginBean> {
            override fun onStart() {
                showLoadingDialog()
            }

            override fun onSuccess(code: Int, data: LoginBean) {
                UserUtils.getInstance().login(data)
                if (isAttach) {
                    baseView.loginSuccess(0, UserUtils.getInstance().loginUser)
                }
            }

            override fun onFailed(code: Int, msg: String) {
                UserUtils.getInstance().logout()
                if (isAttach) {
                    baseView.loginFailed(code, msg)
                }
            }

            override fun onError(handle: ExceptionHandle) {}
            override fun onFinish() {
                dismissLoadingDialog()
            }
        }))
    }

    companion object {
        private const val SP_KEY_LOGIN_INFO = "loginInfo"
        private const val SP_KEY_CIPHER_IV = "cipherIV"
    }
}