package com.example.lieon.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lieon.record.db.RecordHistoryEntity
import com.example.lieon.record.db.RecordRepository
import com.example.lieon.result.model.RecordResults
import com.example.lieon.result.recyclerview.ResultRecyclerViewAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
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