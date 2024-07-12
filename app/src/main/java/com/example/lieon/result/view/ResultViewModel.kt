package com.example.lieon.result.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.lieon.db.RecordHistoryEntity
import com.example.lieon.db.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {
    val recordResults : LiveData<List<RecordHistoryEntity>> = recordRepository.getAllRecordHistories()
//    val recordResults : LiveData<List<RecordHistoryEntity>> get() = _recordResults


    init {
//        updateItems()
    }

//    fun updateItems(){
//        viewModelScope.launch(Dispatchers.IO) {
//            _recordResults.postValue(loadRecordHistory())
//        }
//    }

//    private suspend fun loadRecordHistory() : LiveData<List<RecordHistoryEntity> = recordRepository.getAllRecordHistories()
}