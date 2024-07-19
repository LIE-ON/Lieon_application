package com.example.lieon.result.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lieon.db.RecordHistoryEntity
import com.example.lieon.db.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {
    val recordResults : LiveData<List<RecordHistoryEntity>> = recordRepository.getAllRecordHistories()

    fun deleteRecordResult(position: Int){
        recordResults.value?.let { records ->
            val record = records[position]
            viewModelScope.launch {
                recordRepository.deleteRecordHistory(record)
            }
        }
    }

    fun searchRecordResult(id : Int) : RecordHistoryEntity?{
        recordResults.value?.forEach {
            if (it.id == id){
                return it
            }
        }
        return null
    }


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