package com.example.lieon.result.view.detail

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lieon.db.RecordHistoryEntity
import com.example.lieon.db.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class ResultDetailViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {

    private var _selectedId: Int = 0

    private val _recordHistory = MutableLiveData<RecordHistoryEntity?>()
    val recordHistory: LiveData<RecordHistoryEntity?> get() = _recordHistory

    fun setSelectedId(id: Int) {
        _selectedId = id
        fetchRecordHistoryById(id)
    }

    private fun fetchRecordHistoryById(id: Int) {
        viewModelScope.launch {
            val record = withContext(Dispatchers.IO) {
                recordRepository.searchRecordHistoryById(id)
            }
            _recordHistory.value = record
        }
    }

    suspend fun updateRecordHistory(recordHistoryEntity: RecordHistoryEntity) {
        withContext(Dispatchers.IO) {
            recordRepository.updateRecordHistory(recordHistoryEntity)
        }
        _recordHistory.postValue(recordHistoryEntity)
    }

    fun getSelectedId(): Int {
        return _selectedId
    }
}
