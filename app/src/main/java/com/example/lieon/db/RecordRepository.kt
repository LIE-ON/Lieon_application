package com.example.lieon.db

import kotlinx.coroutines.flow.Flow


interface RecordRepository {
    fun getAllRecordHistories() : Flow<List<RecordHistory>>

    suspend fun insertRecordHistory(recordHistory: RecordHistory)

    suspend fun deleteRecordHistory(recordHistory: RecordHistory)
}