package com.pigeon.cloud.module.login.model

/**
 * @author yangzhikuan
 * @date 2019/5/8
 *
 */
data class UserEntity(
        val email: String?,
        val username: String?,
        val wanid: Int,
        val sex: Int,
        val signature: String?,
        val avatar: String?,
        val cover: String?
)