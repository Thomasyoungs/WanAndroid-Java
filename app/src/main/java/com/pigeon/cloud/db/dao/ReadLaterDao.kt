package com.pigeon.cloud.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pigeon.cloud.db.model.ReadLaterModel

/**
 * @author yangzhikuan
 * @date 2020/3/21
 */
@Dao
interface ReadLaterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg mode: ReadLaterModel)

    @Query("DELETE FROM ReadLaterModel WHERE link = :link")
    suspend fun delete(link: String)

    @Query("DELETE FROM ReadLaterModel")
    suspend fun deleteAll()

    @Query("SELECT * FROM ReadLaterModel ORDER BY time DESC LIMIT (:offset), (:count)")
    suspend fun findAll(offset: Int, count: Int): List<ReadLaterModel>

    @Query("SELECT * FROM ReadLaterModel ORDER BY time DESC LIMIT 0, (:count)")
    suspend fun findLately(count: Int): List<ReadLaterModel>

    @Query("SELECT * FROM ReadLaterModel WHERE link = :link")
    suspend fun findByLink(link: String): List<ReadLaterModel>
}