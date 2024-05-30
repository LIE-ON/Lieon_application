package com.example.lieon.db

import kotlinx.coroutines.flow.Flow

class OfflineRecordRepository(private val recordHistoryDao: RecordHistoryDao) : RecordRepository{
    override fun getAllRecordHistories(): Flow<List<RecordHistory>> = recordHistoryDao.getAllRecordHistories()

    override suspend fun insertRecordHistory(recordHistory: RecordHistory) = recordHistoryDao.insert(recordHistory)

    override suspend fun deleteRecordHistory(recordHistory: RecordHistory) = recordHistoryDao.delete(recordHistory)

}