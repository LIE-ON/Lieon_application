package com.example.lieon.db

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val recordHistoryDatabase : RecordHistoryDatabase
){

    private val recordHistoryDao = recordHistoryDatabase.recordHistoryDao()

    fun getAllRecordHistories(): LiveData<List<RecordHistoryEntity>> = recordHistoryDao.getAllRecordHistories()

    suspend fun insertRecordHistory(recordHistoryEntity: RecordHistoryEntity) = recordHistoryDao.insert(recordHistoryEntity)

    suspend fun deleteRecordHistory(recordHistoryEntity: RecordHistoryEntity) = recordHistoryDao.delete(recordHistoryEntity)

    suspend fun searchRecordHistory(id : Int) : RecordHistoryEntity?{
        Log.d("room db", "search $id")
        val test = recordHistoryDao.searchRecordHistoryById(id)
        Log.d("room db", "searched ${test!!.filePath}")
        return test
    }

    suspend fun deleteAllRecordHistory(){
        recordHistoryDao.deleteAll()
    }

    private fun convertFlowToList(data : Flow<List<RecordHistoryEntity>>): List<RecordHistoryEntity> = runBlocking {
        val result = mutableListOf<RecordHistoryEntity>()
        data.collect { dataList ->
            result.addAll(dataList)
        }
        result
    }

}