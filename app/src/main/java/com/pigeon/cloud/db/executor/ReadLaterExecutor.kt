package com.pigeon.cloud.db.executor

import com.pigeon.basic.core.utils.listener.SimpleCallback
import com.pigeon.basic.core.utils.listener.SimpleListener
import com.pigeon.cloud.db.WanDb.db
import com.pigeon.cloud.db.model.ReadLaterModel

/**
 * @author yangzhikuan
 * @date 2020/3/21
 */
class ReadLaterExecutor : DbExecutor() {

    fun findByLink(link: String, success: com.pigeon.basic.core.utils.listener.SimpleCallback<List<ReadLaterModel>>, error: com.pigeon.basic.core.utils.listener.SimpleListener) {
        execute({
            db().readLaterDao().findByLink(link)
        }, {
            success.onResult(it)
        }, {
            error.onResult()
        })
    }

    fun add(link: String, title: String, success: com.pigeon.basic.core.utils.listener.SimpleCallback<ReadLaterModel>, error: com.pigeon.basic.core.utils.listener.SimpleListener) {
        execute({
            val model = ReadLaterModel(link, title, System.currentTimeMillis())
            db().readLaterDao().insert(model)
            model
        }, {
            success.onResult(it)
        }, {
            error.onResult()
        })
    }

    fun remove(link: String, success: com.pigeon.basic.core.utils.listener.SimpleListener, error: com.pigeon.basic.core.utils.listener.SimpleListener) {
        execute({
            db().readLaterDao().delete(link)
        }, {
            success.onResult()
        }, {
            error.onResult()
        })
    }

    fun removeAll(success: com.pigeon.basic.core.utils.listener.SimpleListener, error: com.pigeon.basic.core.utils.listener.SimpleListener) {
        execute({
            db().readLaterDao().deleteAll()
        }, {
            success.onResult()
        }, {
            error.onResult()
        })
    }

    fun getList(from: Int, count: Int, success: com.pigeon.basic.core.utils.listener.SimpleCallback<List<ReadLaterModel>>, error: com.pigeon.basic.core.utils.listener.SimpleListener) {
        execute({
            db().readLaterDao().findAll(from, count)
        }, {
            success.onResult(it)
        }, {
            error.onResult()
        })
    }

    fun findLately(count: Int, success: com.pigeon.basic.core.utils.listener.SimpleCallback<List<ReadLaterModel>>, error: com.pigeon.basic.core.utils.listener.SimpleListener) {
        execute({
            db().readLaterDao().findLately(count)
        }, {
            success.onResult(it)
        }, {
            error.onResult()
        })
    }
}