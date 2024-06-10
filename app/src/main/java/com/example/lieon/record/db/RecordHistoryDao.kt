package com.example.lieon.record.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordHistoryDao {
    @Query("SELECT * FROM recordHistory")
    fun getAllRecordHistories(): Flow<List<RecordHistory>>

    @Insert
    suspend fun insert(recordHistory: RecordHistory)

    @Delete
    suspend fun delete(recordHistory: RecordHistory)
}
