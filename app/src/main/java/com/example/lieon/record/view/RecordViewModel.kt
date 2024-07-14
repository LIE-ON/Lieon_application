package com.example.lieon.record.view

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lieon.db.RecordHistoryEntity
import com.example.lieon.db.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordRepository : RecordRepository
) : ViewModel() {

    private val _endRecordTime = MutableLiveData<Long>()

    private val endRecordTime: LiveData<Long> get() = _endRecordTime

    private var _isRecording : MutableLiveData<Boolean> = MutableLiveData()

    private var pausedTime : Long = 0L

    private lateinit var currentUri : Uri

    val isRecording : LiveData<Boolean> get() = _isRecording

    fun setRecording(boolean: Boolean) {
        _isRecording.value = boolean
    }

    fun setEndRecordTime(time: Long){
        _endRecordTime.value = time
    }

    fun setCurrentUri(uri:Uri){
        currentUri = uri
    }

    fun getCurrentUri() = currentUri

    fun getAllRecords() = recordRepository.getAllRecordHistories()

    suspend fun insertRecord(recordHistoryEntity: RecordHistoryEntity) = recordRepository.insertRecordHistory(recordHistoryEntity)

    suspend fun deleteRecord(recordHistoryEntity: RecordHistoryEntity) = recordRepository.deleteRecordHistory(recordHistoryEntity)

    suspend fun deleteAllRecord() {
        recordRepository.deleteAllRecordHistory()
    }

}
