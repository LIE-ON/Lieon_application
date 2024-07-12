package com.example.lieon.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lieon.record.db.RecordHistoryEntity
import com.example.lieon.record.db.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordRepository : RecordRepository
) : ViewModel() {

    private val _startRecordTime = MutableLiveData<Long>()

    private val startRecordTime: LiveData<Long> get() = startRecordTime

    private val _endRecordTime = MutableLiveData<Long>()

    private val endRecordTime: LiveData<Long> get() = _endRecordTime

    fun setStartRecordTime(time : Long){
        _startRecordTime.value = time
    }

    fun setEndRecordTime(time: Long){
        _endRecordTime.value = time
    }

    fun getAllRecords() = recordRepository.getAllRecordHistories()

    suspend fun insertRecord(recordHistoryEntity: RecordHistoryEntity) = recordRepository.insertRecordHistory(recordHistoryEntity)

    suspend fun deleteRecord(recordHistoryEntity: RecordHistoryEntity) = recordRepository.deleteRecordHistory(recordHistoryEntity)

    suspend fun deleteAllRecord() {
        recordRepository.deleteAllRecordHistory()
    }

}
