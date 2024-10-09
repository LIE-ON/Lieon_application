package com.example.lieon.record.view

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lieon.db.RecordHistoryEntity
import com.example.lieon.db.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private var _goalAccuracy : MutableLiveData<Int> = MutableLiveData()

    val goalAccuracy : LiveData<Int> get() = _goalAccuracy

    val isRecording : LiveData<Boolean> get() = _isRecording

    init {
        _goalAccuracy.value = 80
    }

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

    fun setGoalAccuracy(int: Int){
        _goalAccuracy.value = int
    }

    fun getAllRecords() = recordRepository.getAllRecordHistories()

    suspend fun insertRecord(recordHistoryEntity: RecordHistoryEntity) = recordRepository.insertRecordHistory(recordHistoryEntity)

    suspend fun deleteRecord(recordHistoryEntity: RecordHistoryEntity) = recordRepository.deleteRecordHistory(recordHistoryEntity)

    suspend fun deleteAllRecord() {
        recordRepository.deleteAllRecordHistory()
    }

    fun updateRecordTitle(recordId: Long, newTitle: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val record = recordRepository.getRecordById(recordId)
            Log.d("RecordUpdate", "Record fetched: $record")
            record?.let {
                it.title = newTitle
                recordRepository.updateRecordHistory(it)
                Log.d("RecordUpdate", "Record updated with new title: $newTitle")
            } ?: Log.d("RecordUpdate", "Record not found with ID: $recordId")
        }
    }

    fun updateRecordFilePath(recordId: Long, newFilePath: String) {
        viewModelScope.launch {
            val record = recordRepository.getRecordById(recordId)
            Log.d("RecordUpdate", "Record fetched: $record")
            record?.let {
                it.filePath = newFilePath
                recordRepository.updateRecordHistory(it)
                Log.d("RecordUpdate", "Record updated with new filePath: $newFilePath")
            } ?: Log.d("RecordUpdate", "Record not found with ID: $recordId")
        }
    }

}

