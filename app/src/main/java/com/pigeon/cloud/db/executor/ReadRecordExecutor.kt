package com.pigeon.cloud.db.executor

import androidx.room.withTransaction
import com.pigeon.basic.core.utils.listener.SimpleCallback
import com.pigeon.basic.core.utils.listener.SimpleListener
import com.pigeon.cloud.common.Config
import com.pigeon.cloud.db.WanDb.db
import com.pigeon.cloud.db.model.ReadRecordModel

/**
 * @author yangzhikuan
 * @date 2020/3/21
 */
class ReadRecordExecutor : DbExecutor() {

    fun findByLinks(
        link: List<String>,
        success: com.pigeon.basic.core.utils.listener.SimpleCallback<List<ReadRecordModel>>,
        error: com.pigeon.basic.core.utils.listener.SimpleCallback<Throwable>
    ) {
        execute({
            db().readRecordDao().findByLinks(link)
        }, {
            success.onResult(it)
        }, {
            error.onResult(it)
        })
    }

    fun add(
        link: String,
        title: String,
        percent: Float,
        success: com.pigeon.basic.core.utils.listener.SimpleCallback<ReadRecordModel>,
        error: com.pigeon.basic.core.utils.listener.SimpleListener
    ) {
        val time = System.currentTimeMillis()
        val model = ReadRecordModel(
            link, title, time, time,
            (percent * ReadRecordModel.MAX_PERCENT).toInt()
        )
        execute({
            db().readRecordDao().insert(model)
        }, {
            success.onResult(model)
            removeIfMaxCount {}
        }, {
            error.onResult()
        })
    }

    fun updatePercent(
        link: String,
        percent: Float,
        lastTime: Long,
        success: com.pigeon.basic.core.utils.listener.SimpleCallback<ReadRecordModel>,
        error: com.pigeon.basic.core.utils.listener.SimpleListener
    ) {
        val p = (percent.coerceIn(0f, 1f) * ReadRecordModel.MAX_PERCENT).toInt()
        execute({
            val db = db()
            db.withTransaction {
                val dao = db.readRecordDao()
                dao.updatePercent(link, p, lastTime)
                dao.findByLink(link)!!
            }
        }, {
            success.onResult(it)
        }, {
            error.onResult()
        })
    }

    fun remove(link: String, success: com.pigeon.basic.core.utils.listener.SimpleListener, error: com.pigeon.basic.core.utils.listener.SimpleListener) {
        execute({
            db().readRecordDao().delete(link)
        }, {
            success.onResult()
        }, {
            error.onResult()
        })
    }

    fun removeAll(success: com.pigeon.basic.core.utils.listener.SimpleListener, error: com.pigeon.basic.core.utils.listener.SimpleListener) {
        execute({
            db().readRecordDao().deleteAll()
        }, {
            success.onResult()
        }, {
            error.onResult()
        })
    }

    fun getList(
        from: Int,
        count: Int,
        success: com.pigeon.basic.core.utils.listener.SimpleCallback<List<ReadRecordModel>>,
        error: com.pigeon.basic.core.utils.listener.SimpleListener
    ) {
        execute({
            db().readRecordDao().findAll(from, count)
        }, {
            success.onResult(it)
        }, {
            error.onResult()
        })
    }

    private fun removeIfMaxCount(finish: () -> Unit) {
        execute({ db().readRecordDao().deleteIfMaxCount(Config.READ_RECORD_MAX_COUNT) }, {
            finish.invoke()
        }, {
            finish.invoke()
        })
    }
}