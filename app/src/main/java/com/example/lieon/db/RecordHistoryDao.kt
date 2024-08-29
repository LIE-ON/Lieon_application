package com.example.lieon.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordHistoryDao {
    @Query("SELECT * FROM recordHistory")
    fun getAllRecordHistories(): LiveData<List<RecordHistoryEntity>>

    @Insert
    suspend fun insert(recordHistoryEntity: RecordHistoryEntity)

    @Delete
    suspend fun delete(recordHistoryEntity: RecordHistoryEntity)

    @Query("SELECT * FROM recordHistory WHERE id = :id")
    suspend fun searchRecordHistoryById(id: Int): RecordHistoryEntity?

    @Query("SELECT * FROM recordHistory WHERE title LIKE '%' || :title || '%'")
    suspend fun searchRecordHistoryByTitle(title : String) : List<RecordHistoryEntity>

    @Query("DELETE FROM recordHistory")
    suspend fun deleteAll()


}
