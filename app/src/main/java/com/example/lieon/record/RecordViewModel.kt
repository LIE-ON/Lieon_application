package com.example.lieon.record

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lieon.record.db.RecordHistory
import com.example.lieon.record.db.RecordRepository

class RecordViewModel(
    private val application: Application
) : ViewModel() {
    private val recordRepository : RecordRepository = RecordRepository(application)

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

    suspend fun insertRecord(recordHistory: RecordHistory) = recordRepository.insertRecordHistory(recordHistory)

    suspend fun deleteRecord(recordHistory: RecordHistory) = recordRepository.deleteRecordHistory(recordHistory)

}
