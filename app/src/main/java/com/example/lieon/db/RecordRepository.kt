package com.example.lieon.db

import androidx.lifecycle.LiveData
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val recordHistoryDatabase : RecordHistoryDatabase
){

    private val recordHistoryDao = recordHistoryDatabase.recordHistoryDao()

    fun getAllRecordHistories(): LiveData<List<RecordHistoryEntity>> = recordHistoryDao.getAllRecordHistories()

    suspend fun insertRecordHistory(recordHistoryEntity: RecordHistoryEntity) = recordHistoryDao.insert(recordHistoryEntity)

    suspend fun deleteRecordHistory(recordHistoryEntity: RecordHistoryEntity) = recordHistoryDao.delete(recordHistoryEntity)

    suspend fun deleteRecordHistoryById(selectID : Int) = recordHistoryDao.deleteById(selectID)

    suspend fun searchRecordHistoryById(id : Int) : RecordHistoryEntity? = recordHistoryDao.searchRecordHistoryById(id)

    suspend fun searchRecordHistoryByTitle(title : String) : List<RecordHistoryEntity> = recordHistoryDao.searchRecordHistoryByTitle(title)

    suspend fun deleteAllRecordHistory(){
        recordHistoryDao.deleteAll()
    }

    suspend fun updateRecordHistory(recordHistoryEntity: RecordHistoryEntity) {
        recordHistoryDao.updateRecordHistory(recordHistoryEntity)
    }

//    private fun convertFlowToList(data : Flow<List<RecordHistoryEntity>>): List<RecordHistoryEntity> = runBlocking {
//        val result = mutableListOf<RecordHistoryEntity>()
//        data.collect { dataList ->
//            result.addAll(dataList)
//        }
//        result
//    }

}