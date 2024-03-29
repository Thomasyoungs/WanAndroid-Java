package com.pigeon.cloud.db

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import com.pigeon.cloud.db.migration.Migration_1_2

/**
 * @author yangzhikuan
 * @date 2020/3/21
 */
@SuppressLint("StaticFieldLeak")
object WanDb {
    private lateinit var context: Context
    private var database: WanRoom? = null

    @JvmStatic
    fun init(context: Context) {
        this.context = context.applicationContext
    }

    @JvmStatic
    fun db(): WanRoom {
        if (database?.isOpen == true) {
            return database!!
        }
        database = Room
            .databaseBuilder(context, WanRoom::class.java, WanRoom.NAME)
            .addMigrations(Migration_1_2())
            .build()
        return database!!
    }

}