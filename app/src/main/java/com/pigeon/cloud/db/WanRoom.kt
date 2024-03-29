package com.pigeon.cloud.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pigeon.cloud.db.dao.ReadLaterDao
import com.pigeon.cloud.db.dao.ReadRecordDao
import com.pigeon.cloud.db.model.ReadLaterModel
import com.pigeon.cloud.db.model.ReadRecordModel

/**
 * @author yangzhikuan
 * @date 2020/3/21
 */
@Database(
        entities = [
            ReadLaterModel::class,
            ReadRecordModel::class
        ],
        version = WanRoom.VERSION,
        exportSchema = false,
)
abstract class WanRoom : RoomDatabase() {
    companion object {
        const val NAME = "wan_db"
        const val VERSION = 2
    }

    abstract fun readLaterDao(): ReadLaterDao
    abstract fun readRecordDao(): ReadRecordDao
}