package com.example.lieon.result.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lieon.db.RecordHistoryEntity
import com.example.lieon.db.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {
    val recordResults : LiveData<List<RecordHistoryEntity>> = recordRepository.getAllRecordHistories()

    fun deleteRecordResultById(selectedID: Int){
        viewModelScope.launch {
            recordRepository.deleteRecordHistoryById(selectedID)
        }
    }
}