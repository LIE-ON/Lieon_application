package com.example.lieon.record.db

import android.app.Application
import kotlinx.coroutines.flow.Flow

class RecordRepository(
    private val application: Application
){
    private val recordHistoryDatabase : RecordHistoryDatabase =
        RecordHistoryDatabase.getInstance(application)
    private val recordHistoryDao = recordHistoryDatabase.recordHistoryDao()

    fun getAllRecordHistories(): Flow<List<RecordHistory>> = recordHistoryDao.getAllRecordHistories()

    suspend fun insertRecordHistory(recordHistory: RecordHistory) = recordHistoryDao.insert(recordHistory)

    suspend fun deleteRecordHistory(recordHistory: RecordHistory) = recordHistoryDao.delete(recordHistory)

}