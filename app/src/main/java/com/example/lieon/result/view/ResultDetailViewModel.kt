package com.example.lieon.result.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lieon.db.RecordHistoryEntity
import com.example.lieon.db.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ResultDetailViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {

    private var _selectedId : Int = 0

    fun setSelectedId(id: Int){
        _selectedId = id
    }

    suspend fun searchResult(id: Int): RecordHistoryEntity? {
        return recordRepository.searchRecordHistory(id)
    }
}