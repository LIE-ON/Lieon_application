package com.example.lieon.result.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lieon.db.RecordHistoryDao
import com.example.lieon.db.RecordHistoryEntity
import com.example.lieon.db.RecordRepository
import com.google.android.gms.drive.query.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ResultViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {

//    val recordResults: LiveData<List<RecordHistoryEntity>> =
//        recordRepository.getAllRecordHistories()

    private val _recordResults = MediatorLiveData<List<RecordHistoryEntity>>()
    val recordResults: LiveData<List<RecordHistoryEntity>> get() = _recordResults

    private val _sortButtonText = MutableLiveData<String>()
    val sortButtonText: LiveData<String> get() = _sortButtonText

    private var isSortedByLatest = true

    init {
        loadRecords()
        updateButtonText()
    }

    fun toggleSorting() {
        isSortedByLatest = !isSortedByLatest
        loadRecords()
        updateButtonText()
    }

    private fun loadRecords() {
        _recordResults.removeSource(recordRepository.getAllRecordHistories())

        val source = recordRepository.getAllRecordHistories()
        _recordResults.addSource(source) { records ->
            _recordResults.value = if (isSortedByLatest) {
                records.sortedByDescending { it.time }
            } else {
                records.sortedBy { it.time }
            }
        }
    }
    private fun updateButtonText() {
        _sortButtonText.value = if (isSortedByLatest) {
            "오래된순"
        } else {
            "최신순"
        }
    }

    fun deleteRecordResultById(selectedID: Int){
        viewModelScope.launch {
            recordRepository.deleteRecordHistoryById(selectedID)
        }
    }
}